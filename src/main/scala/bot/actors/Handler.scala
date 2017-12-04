package bot.actors

import akka.actor.{Actor, Props}
import akka.util.Timeout
import bot.trie.Trie

object Handler {
  def props(implicit timeout: Timeout) = Props(new Handler)
  def name = "handler"

  case class Handle(trie: Trie,
                    msg: String,
                    humanLog: List[String],
                    botLog: List[String])

  case class BotResponse(msg: String)

}

class Handler(implicit timeout: Timeout) extends Actor{
  override def receive = ???
}
