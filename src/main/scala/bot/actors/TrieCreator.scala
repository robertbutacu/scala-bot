package bot.actors

import akka.actor.{Actor, Props}
import akka.util.Timeout
import bot.trie.Trie
import bot.trie.TrieOperations.Word

object TrieCreator{
  def props(implicit timeout: Timeout) = Props(new TrieCreator)

  case class Add(message: List[Word],
                 replies: (Option[() => Set[String]], Set[() => Set[String]]),
                 trie: Trie)


  case class Search(message: List[Word], trie: Trie)


  case class TrieResponse(trie: Trie)


  case object Print
}

class TrieCreator(implicit timeout: Timeout) extends Actor{
  override def receive = ???
}
