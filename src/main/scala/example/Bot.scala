package example

import bot.handler.MessageHandler
import bot.memory.{BotMemory, Person}
import bot.trie.Attribute
import example.brain.Manager
import example.brain.modules.{AgeAttr, JobAttr, NameAttr, PassionAttr}


class Bot extends Manager with MessageHandler with BotMemory {
  def startDemo(): Unit = {
    def go(botLog: List[String] = List.empty,
           humanLog: List[String] = List.empty,
           people: List[Map[Attribute, String]]): Unit = {
      val message = scala.io.StdIn.readLine()
      if (message == "QUIT") {
        persist(add(people, currentSessionInformation.toMap) map (new Person(_)), "out.xml")
      }
      else {
        val updatedHumanLog = humanLog :+ message
        val updatedBotLog = botLog :+ handle(masterBrain, message, updatedHumanLog, botLog)
        println(updatedBotLog.last)

        go(updatedBotLog, humanLog, people)
      }
    }

    val peopleXML = remember("out.xml")

    val people = peopleXML map translate map(e => e.flatten.toMap)

    go(people = people)
  }

  /** The triple represents:
    * _1 : Attribute name
    * _2 : Attribute weight
    * _3 : Attribute value
    *
    * @param people - a list where every single element represent a person with all their traits
    * @return - every item from the list converted to a map of Attribute, String
    */
  override def translate(people: List[(String, String, String)]): List[Map[Attribute, String]] = {
    val applier: PartialFunction[(String, String, String), Map[Attribute, String]] = {
      case ("AgeAttr", weight, ageValue)  => Map(Attribute(AgeAttr, weight.toInt) -> ageValue)
      case ("NameAttr", weight, nameValue) => Map(Attribute(NameAttr, weight.toInt) -> nameValue)
      case ("PassionAttr", weight, passionValue) => Map(Attribute(PassionAttr, weight.toInt) -> passionValue)
      case ("Job", weight, jobValue) => Map(Attribute(JobAttr, weight.toInt) -> jobValue)
    }

    people filter applier.isDefinedAt map applier
  }

  override def disapprovalMessages: Set[String] = Set("", "", "Changed the subject...")

  override def unknownHumanMessages: Set[String] = Set("Not familiar with this")
}
