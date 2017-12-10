package bot.actors

import akka.actor.{Actor, Props}
import bot.actors.TrieCreator._
import bot.learn.RepliesLearner.Word
import bot.learn.{PossibleReply, SearchResponses}
import bot.trie.SpeakingKnowledge

object TrieCreator {
  def props() = Props(new TrieCreator)

  def name() = "trieCreator"

  case class Add(message: List[Word],
                 replies: PossibleReply,
                 trie: SpeakingKnowledge)


  case class Search(message: List[Word], trie: SpeakingKnowledge)


  case class TrieResponse(trie: SpeakingKnowledge)


  case class CreateTrie(message: List[Word],
                        replies: PossibleReply)

  case class Print(trie: SpeakingKnowledge)

  case class SearchReturnMessage(response: SearchResponses)

}

class TrieCreator() extends Actor {
  override def receive: Actor.Receive = {
    case Print(trie: SpeakingKnowledge) => trie.print()

    case CreateTrie(msg, replies) => sender() ! TrieResponse(SpeakingKnowledge().add(msg, replies))

    case Search(msg, trie) => sender() ! SearchReturnMessage(trie.search(msg))

    case Add(msg, replies, trie) => sender() ! TrieResponse(trie.add(msg, replies))
  }
}
