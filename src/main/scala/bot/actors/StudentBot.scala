package bot.actors

import akka.actor.{Actor, Props}
import bot.learn.Reply
import bot.trie.SpeakingKnowledge

object StudentBot {
  def props() = Props(new StudentBot)
  def name() = "studentBot"

  case class LearnReplies(trie: SpeakingKnowledge, acquired: List[Reply])

  case class CreatedTrie(trie: SpeakingKnowledge)
}

class StudentBot() extends Actor{
  override def receive: Actor.Receive = {
    case _ => "asdasd"
  }
}
