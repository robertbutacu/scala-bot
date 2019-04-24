package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.TrieCreator._
import bot.learn.{PossibleReply, SearchResponses}
import bot.memory.Trie
import bot.memory.storage.Printer.TriePrinter
import bot.memory.storage.MemoryLookup.TrieLookup
import bot.memory.definition.{NodeSimpleWord, PartOfSentence}
import bot.memory.storage.MemoryStorer.TrieMemoryStorer

object TrieCreator {
  def props() = Props(new TrieCreator)

  def name() = "trieCreator"

  case class Add(message: List[PartOfSentence],
                 replies: PossibleReply,
                 trie: Trie)


  case class Search(message: String, trie: Trie)


  case class TrieResponse(trie: Trie)


  case class CreateTrie(message: List[PartOfSentence],
                        replies: PossibleReply)

  case class Print(trie: Trie)

  case class SearchReturnMessage(response: SearchResponses)

}

class TrieCreator() extends Actor {
  override def receive: Actor.Receive = {
    case Print(trie: Trie)        => trie.print()

    case CreateTrie(msg, replies) => sender() ! TrieResponse(Trie(NodeSimpleWord("".r)).add(msg, replies))

    case Search(msg, trie)        => sender() ! SearchReturnMessage(trie.search(msg))

    case Add(msg, replies, trie)  => sender() ! TrieResponse(trie.add(msg, replies))
  }
}
