package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.Handler.{Hello, World}
import bot.trie.Trie

object Handler {
  def props() = Props(new Handler)
  def name() = "handler"

  case class Handle(trie: Trie,
                    msg: String,
                    humanLog: List[String],
                    botLog: List[String])

  case class BotResponse(msg: String)

  case object Hello

  case object World{
    override def toString = "World"
  }
}

class Handler() extends Actor{
  override def receive: Actor.Receive = {
    case Hello => sender() ! World
  }
}
