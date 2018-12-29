package bot.actors

import akka.actor.{Actor, Props}
import bot.learn.MessageTemplate
import bot.memory.Trie

object StudentBot {
  def props() = Props(new StudentBot)
  def name() = "studentBot"

  case class LearnReplies(trie: Trie, acquired: List[MessageTemplate])

  case class CreatedTrie(trie: Trie)
}

class StudentBot() extends Actor{
  override def receive: Actor.Receive = {
    case _ => "asdasd"
  }
}
