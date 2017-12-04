package bot.actors

import akka.actor.{Actor, Props}
import bot.learn.Reply
import bot.trie.Trie

object StudentBot {
  def props() = Props(new StudentBot)
  def name() = "studentBot"

  case class LearnReply(trie: Trie, acquired: List[Reply])

}

class StudentBot() extends Actor{
  override def receive: Actor.Receive = {
    case _ => "asdasd"
  }
}
