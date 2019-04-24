package bot.memory.storage

import bot.connections.Attribute
import bot.learn.SearchResponses
import bot.memory.Trie
import bot.memory.definition.PartOfSentence

import scala.annotation.tailrec

trait MemoryLookup {
  def search(message: List[PartOfSentence]): SearchResponses
}

object MemoryLookup {

  implicit class TrieLookup(trie: Trie) extends MemoryLookup {
    /**
      * The algorithm describes the search of a message in a trie, by parsing every word and matching it,
      * thus returning a Set of possible replies depending on bot's previous replies.
      *
      * @param message - the sentence that is to be found, or not
      * @return - returns a Set of (previousMessageFromBot, Set[functions returning possible replies]),
      *         from which another algorithm will pick the best choice.
      */
    override def search(message: List[PartOfSentence]): SearchResponses = {
      @tailrec
      def go(message:    List[PartOfSentence],
             trie:       Trie,
             attributes: Map[Attribute, String]): SearchResponses = {
        if (message.isEmpty)
          SearchResponses(attributes, trie.replies) //completely ran over all the words
        else {
          val head = message.head

          trie.children.find(t => t.information.wordMatches(head)) match {
            case None           => SearchResponses(attributes) //word wasn't found in the trie
            case Some(nextNode) => go(message.tail, nextNode, nextNode.information.addToAttributes(head.word.toString(), attributes))
          }
        }
      }

      go(message, trie, Map[Attribute, String]().empty)
    }
  }
}
