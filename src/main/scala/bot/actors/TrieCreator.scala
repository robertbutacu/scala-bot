package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.TrieCreator._
import bot.trie.Trie
import bot.trie.TrieOperations._

object TrieCreator {
  def props() = Props(new TrieCreator)

  def name() = "trieCreator"

  case class Add(message: List[Word],
                 replies: (Option[() => Set[String]], Set[() => Set[String]]),
                 trie: Trie)


  case class Search(message: List[Word], trie: Trie)


  case class TrieResponse(trie: Trie)


  case class CreateTrie(message: List[Word],
                        replies: (Option[() => Set[String]], Set[() => Set[String]]))

  case class Print(trie: Trie)

  case class SearchReturnMessage(response: SearchResponse)

}

class TrieCreator() extends Actor {
  override def receive: Actor.Receive = {
    case Print(trie: Trie) => printTrie(trie)

    case CreateTrie(msg, replies) => sender() ! TrieResponse(add(msg, replies, Trie()))

    case Search(msg, trie) => sender() ! SearchReturnMessage(search(msg, trie))

    case Add(msg, replies, trie) => sender() ! TrieResponse(add(msg, replies, trie))
  }
}
