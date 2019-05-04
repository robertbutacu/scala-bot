package example

import bot.connections.{Acquaintances, Attribute}
import bot.handler.{MessageHandler, SessionInformation}
import cats.Monad
import cats.syntax.all._
import example.brain.Manager

import scala.annotation.tailrec
import scala.language.higherKinds

case class Bot[F[_]](minKnowledgeThreshold: Int) extends Manager with MessageHandler {
  type Matcher = (Option[Map[Attribute, String]], List[String], List[String])

  implicit def acquaintances(implicit M: Monad[F]): Acquaintances[F] = Acquaintances.xmlStorage[F]("out.xml")

  def startDemo(implicit M: Monad[F]): Unit = {
    def go(botLog:   List[String] = List.empty,
           humanLog: List[String] = List.empty): Unit = {
      val message = scala.io.StdIn.readLine()
      if (message == "QUIT") acquaintances.add(currentSessionInformation.toMap)
      else {
        val updatedHumanLog = humanLog :+ message

        if (message == "Do you remember me?") {
          val possibleMatches = acquaintances.tryMatch(currentSessionInformation.toList, minKnowledgeThreshold)

          possibleMatches
            .map(pm => matcher(pm, humanLog, botLog))
            .map {
              case (None, bL, hL)    => go(bL, hL)
              case (Some(p), bL, hL) =>
                currentSessionInformation = currentSessionInformation.empty ++ p
                acquaintances.forget(p).map(_ => go(bL, hL))
            }
        }

        else {
          val updatedBotLog = botLog :+ handle(masterBrain, message, updatedHumanLog, botLog)
          println(updatedBotLog.last)

          go(updatedBotLog, humanLog)
        }
      }
    }

    go()
  }

  //TODO this is rather tricky - have maybe a separate brain module which deals with remembering people ...? More generic this way
  @tailrec
  final def matcher(people:   List[Map[Attribute, String]],
                    humanLog: List[String],
                    botLog:   List[String]): Matcher = {
    if (people.isEmpty) {
      val response = "Sorry, I do not seem to remember you."
      println(response)
      (None, humanLog, botLog :+ response)
    }
    else {
      val botMsg = "Does this represent you: " + people.head
        .filterNot(currentSessionInformation.toList.contains)
        .maxBy(_._1.weight)._2
      println(botMsg)

      val userMsg = scala.io.StdIn.readLine()

      if (userMsg == "Yes") {
        println("Ah, welcome back!")
        (Some(people.head), humanLog :+ userMsg, botLog :+ botMsg)
      }
      else
        matcher(people.tail, humanLog :+ userMsg, botLog :+ botMsg)
    }
  }
/*
  override def disapprovalMessages:  Set[String] = Set("", "", "Changed the subject...")

  override def unknownHumanMessages: Set[String] = Set("Not familiar with this")*/
  override def sessionInformation: SessionInformation =
    SessionInformation(masterBrain,
      Set("Not familiar with this"),
      Set("", "", "Changed the subject..."))
}
