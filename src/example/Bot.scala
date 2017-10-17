package example

import example.brain.{BrainFunctions, Manager}

import scala.bot.handler.MessageHandler
import scala.util.control.Breaks._

trait Bot extends Manager with MessageHandler{
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
