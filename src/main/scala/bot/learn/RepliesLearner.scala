package bot.learn

import bot.memory.Trie
import bot.memory.definition.{Definition, PartOfSentence}
import bot.memory.storage.MemoryStorer.TrieMemoryStorer

import scala.annotation.tailrec

object RepliesLearner {
  type Responses = Set[() => Set[String]]

  /**
    * @param trie     - previous trie to which new templates are to be added
    * @param acquired - a list of replies to be added
    * @return - a new trie with the list of acquired replies in memory
    */
  def learn[A](trie: Trie, acquired: List[MessageTemplate], dictionary: Set[Definition]): Trie = {
    @tailrec
    def startLearning(curr: Trie, toBeLearned: List[MessageTemplate]): Trie = {
      toBeLearned match {
        case Nil       => curr
        case h :: tail => startLearning(learn(curr, h), tail)
      }
    }

    /**
      * @param trie - previous trie to which new templates are to be added
      * @param r    - reply
      * @return - a new trie with the acquired reply in memory
      */
    def learn(trie: Trie, r: MessageTemplate): Trie = {
      trie.add(toWords(r.humanMessage.message), PossibleReply(r), dictionary)
    }

    startLearning(trie, acquired)
  }

  /**
    * The anonymous function creates a List of Lists of (Regex, Some(Attribute)),
    * which will be flattened => a list of words. The list is then filtered to not contain any
    * empty words ( "" ).
    *
    * @param message - message to be added in the trie that needs to be split
    * @return a list of words that could be either a string with no attr set,
    *         or a regex with an attribute
    */
  private def toWords(message: List[Message]): List[PartOfSentence] =
    message flatMap { w =>
      w.pattern.toString
        .split(" ")
        .toList
        .withFilter(_ != "")
        .map(p => PartOfSentence(p.r, w.attribute))
    }
}
