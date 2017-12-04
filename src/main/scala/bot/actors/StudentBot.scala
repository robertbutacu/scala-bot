package bot.actors

import akka.actor.{Actor, Props}
import akka.util.Timeout
import bot.learn.Reply
import bot.trie.Trie

object StudentBot {
  def props(implicit timeout: Timeout) = Props(new StudentBot)
  def name = "studentBot"

  case class LearnReply(trie: Trie, acquired: List[Reply])

}

class StudentBot(implicit timeout: Timeout) extends Actor{
  override def receive = ???
}
