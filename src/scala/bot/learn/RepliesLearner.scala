package scala.bot.learn

import scala.annotation.tailrec
import scala.bot.trie.TrieOperations._
import scala.bot.trie.{PartOfMessage, Reply, Trie}

object RepliesLearner {
  type Responses = Set[() => Set[String]]

  /**
    * @param trie     - previous trie to which new templates are to be added
    * @param acquired - templates to be added
    * @return - a new trie with the acquired templates in memory
    */
  final def learn(trie: Trie, acquired: Reply): Trie =
    add(toWords(acquired.humanMessage.message),
      (acquired.humanMessage.previousBotReply, acquired.botReplies),
      trie)


  /**
    * @param old      - previous trie to which new templates are to be added
    * @param acquired - a list of templates to be added
    * @return - a new trie with the list of acquired templates in memory
    */
  def learn(old: Trie, acquired: List[Reply]): Trie = {
    @tailrec
    def startLearning(curr: Trie, toBeLearned: List[Reply]): Trie = {
      toBeLearned match {
        case Nil => curr
        case h :: tail => startLearning(learn(curr, h), tail)
      }
    }

    startLearning(old, acquired)
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
  def toWords(message: List[PartOfMessage]): List[Word] =
    message flatMap { w =>
      w.part match {
        case Left(words) => words.split(' ').toList.map(w => (w.r, None))
        case Right((r, characteristic)) => List((r, Some(characteristic)))
      }
    } filterNot (_._1.toString() == "")
}
