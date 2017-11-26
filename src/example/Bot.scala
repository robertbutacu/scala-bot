package example

import example.brain.Manager

import scala.bot.handler.MessageHandler
import scala.bot.memory.BotMemory
import scala.util.control.Breaks._

class Bot extends Manager with MessageHandler with BotMemory{
  def startDemo(): Unit = {
    disapprovalMessages = Set("", "", "Changed the subject...")
    breakable {
      while (true) {
        val message = scala.io.StdIn.readLine()
        if (message == "QUIT") break()
        else println(handle(masterBrain, message))
      }
    }
  }
}
