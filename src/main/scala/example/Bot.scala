package example

import bot.handler.MessageHandler
import bot.memory.{BotMemory, Person}
import bot.trie.Attribute
import example.brain.Manager
import example.brain.modules.{AgeAttr, JobAttr, NameAttr, PassionAttr}


class Bot extends Manager with MessageHandler with BotMemory {
  def startDemo(): Unit = {
    disapprovalMessages = Set("", "", "Changed the subject...")

    /*persist(List(new Person(Map(Attribute(AgeAttr, 10) -> "123",
      Attribute(AgeAttr, 15) -> "12",
      Attribute(AgeAttr, 14) -> "12")),
      new Person(Map(Attribute(AgeAttr, 10) -> "123",
        Attribute(AgeAttr, 15) -> "12",
        Attribute(AgeAttr, 14) -> "12"))), "out.xml")*/

    val peopleXML = remember("out.xml")

    val people = peopleXML map translate

    people.foreach(println)

    /*println(tryMatch(
      List(Map(Attribute(AgeAttr, 10) -> "123",
        Attribute(AgeAttr, 15) -> "12",
        Attribute(AgeAttr, 14) -> "14"),
        Map(Attribute(AgeAttr, 10) -> "123",
          Attribute(AgeAttr, 15) -> "12",
          Attribute(AgeAttr, 14) -> "13")),
      Map(Attribute(AgeAttr, 15) -> "12",
        Attribute(AgeAttr, 14) -> "13").toList,
      15
    ))*/
    /*breakable {
      while (true) {
        val message = scala.io.StdIn.readLine()
        if (message == "QUIT") break()
        else println(handle(masterBrain, message))
      }
    }*/
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
}
