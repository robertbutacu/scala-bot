package example

import bot.handler.MessageHandler
import bot.memory.{BotMemory, Person}
import bot.trie.Attribute
import example.brain.Manager
import example.brain.modules.AgeAttr

class Bot extends Manager with MessageHandler with BotMemory {
  def startDemo(): Unit = {
    disapprovalMessages = Set("", "", "Changed the subject...")
    val person = new Person(Map(Attribute(AgeAttr, 5) -> "32"))
    println(person.toXml)
    /*breakable {
      while (true) {
        val message = scala.io.StdIn.readLine()
        if (message == "QUIT") break()
        else println(handle(masterBrain, message))
      }
    }*/
  }
}
