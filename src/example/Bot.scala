package example

import example.brain.Manager

import scala.bot.MessageHandler
import util.control.Breaks._

trait Bot extends Manager with MessageHandler {
  def startDemo(): Unit = {
    breakable {
      while (true) {
        val message = scala.io.StdIn.readLine()
        if (message == "QUIT") break()
        else println(handle(masterBrain, message))
      }
    }
  }
}
