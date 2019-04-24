package example

import bot.connections.{Acquaintances, Attribute, Person}
import bot.handler.MessageHandler
import bot.memory.storage.Printer.TriePrinter
import cats.{Applicative, Monad}
import example.brain.Manager
import example.brain.modules.{AgeAttr, JobAttr, NameAttr, PassionAttr}
import cats.syntax.all._

import scala.annotation.tailrec
import scala.language.higherKinds
import scala.util.{Failure, Success}


case class Bot[F[_]]() extends Manager with MessageHandler {
  type Matcher = (Option[Map[Attribute, String]], List[String], List[String])

  implicit def acquaintances(implicit A: Applicative[F]): Acquaintances[F] = Acquaintances.xmlStorage[F]("out.xml")

  def startDemo(implicit M: Monad[F]): Unit = {
    def go(botLog: List[String] = List.empty,
           humanLog: List[String] = List.empty,
           people: List[Map[Attribute, String]]): Unit = {
      val message = scala.io.StdIn.readLine()
      if (message == "QUIT") {
        acquaintances.add(people, currentSessionInformation.toMap)
          .map(people => people.map(p => Person(p)))
          .map(person => acquaintances.persist(person))
      }
      else {
        val updatedHumanLog = humanLog :+ message

        if (message == "Do you remember me?") {
          val possibleMatches = acquaintances.tryMatch(people, currentSessionInformation.toList, 10)

          val isMatch = possibleMatches.map(pm => matcher(pm, humanLog, botLog))
          isMatch.map {
            case (None, bL, hL) => go(bL, hL, people)
            case (Some(p), bL, hL) =>
              currentSessionInformation = currentSessionInformation.empty ++ p
              acquaintances.forget(people, p).map(p => go(bL, hL, p))
          }
        }

        else {
          val updatedBotLog = botLog :+ handle(masterBrain, message, updatedHumanLog, botLog)
          println(updatedBotLog.last)

          go(updatedBotLog, humanLog, people)
        }
      }
    }

    val peopleXML = acquaintances.remember()

    val people = peopleXML.map {
      case Success(p) => p.view.map(translate).map(_.flatten.toMap).toList
      case Failure(e) => println(s"There seem to be a problem loading up my memory: $e ..."); List.empty
    }

    masterBrain.print()
    people.map(p => go(people = p))
  }

  @tailrec
  final def matcher(people: List[Map[Attribute, String]],
                    humanLog: List[String],
                    botLog: List[String]): Matcher = {
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

  /** The triple represents:
    * _1 : Attribute name
    * _2 : Attribute weight
    * _3 : Attribute value
    *
    * @param people - a list where every single element represent a person with all their traits
    * @return - every item from the list converted to a map of Attribute, String
    */
  def translate(people: List[(String, String, String)]): List[Map[Attribute, String]] = {
    val applier: PartialFunction[(String, String, String), Map[Attribute, String]] = {
      case ("AgeAttr", weight, ageValue)         => Map(Attribute(AgeAttr, weight.toInt) -> ageValue)
      case ("NameAttr", weight, nameValue)       => Map(Attribute(NameAttr, weight.toInt) -> nameValue)
      case ("PassionAttr", weight, passionValue) => Map(Attribute(PassionAttr, weight.toInt) -> passionValue)
      case ("Job", weight, jobValue)             => Map(Attribute(JobAttr, weight.toInt) -> jobValue)
    }

    people collect applier
  }

  override def disapprovalMessages: Set[String] = Set("", "", "Changed the subject...")

  override def unknownHumanMessages: Set[String] = Set("Not familiar with this")
}
