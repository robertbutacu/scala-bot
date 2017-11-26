package bot.learn

import bot.trie.TrieOperations._
import bot.trie.{Attribute, Trie}

import scala.annotation.tailrec
import scala.util.matching.Regex

object RepliesLearner {
  type Responses = Set[() => Set[String]]


  /**
    * @param trie     - previous trie to which new templates are to be added
    * @param acquired - a list of replies to be added
    * @return - a new trie with the list of acquired replies in memory
    */
  def learn(trie: Trie, acquired: List[Reply]): Trie = {
    @tailrec
    def startLearning(curr: Trie, toBeLearned: List[Reply]): Trie = {
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
    def learn(trie: Trie, r: Reply): Trie =
      add(toWords(r.humanMessage.message),
        (r.humanMessage.previousBotReply, r.botReplies),
        trie)

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
  def toWords(message: List[(Regex, Option[Attribute])]): List[Word] =
    message flatMap { w =>
      w._1.toString.split(" ").toList.map(p => (p.r, w._2))
    } filterNot (_._1.toString == "")
}
