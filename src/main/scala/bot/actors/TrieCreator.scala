package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.TrieCreator.Print
import bot.trie.Trie
import bot.trie.TrieOperations.Word

object TrieCreator {
  def props() = Props(new TrieCreator)

  def name() = "trieCreator"

  case class Add(message: List[Word],
                 replies: (Option[() => Set[String]], Set[() => Set[String]]),
                 trie: Trie)


  case class Search(message: List[Word], trie: Trie)


  case class TrieResponse(trie: Trie)


  case object Print

}

class TrieCreator() extends Actor {
  override def receive: Actor.Receive = {
    case Print => println("asdfasd")
  }
}
