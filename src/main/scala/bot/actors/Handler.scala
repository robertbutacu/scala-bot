package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.Handler._
import bot.handler.MessageHandler
import bot.memory.SpeakingKnowledge


object Handler {
  def props() = Props(new Handler)

  def name() = "handler"

  case class Handle(trie: SpeakingKnowledge,
                    msg: String,
                    humanLog: List[String],
                    botLog: List[String])

  case class BotResponse(msg: String)

  case object Hello

  case object World {
    override def toString = "World"
  }
}

class Handler() extends Actor with MessageHandler {
  override def receive: Actor.Receive = {
    case Hello => Thread.sleep(3000); sender() ! World

    case Handle(trie, msg, humanLog, botLog) => sender() ! BotResponse(handle(trie, msg, humanLog, botLog))
  }
}
