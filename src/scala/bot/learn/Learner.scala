package scala.bot.learn

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.bot.trie.Trie
import scala.bot.trie.TrieOperations._
import scala.util.matching.Regex

object Learner {
  /**
    * Templates are composed of a Map of :
    *   - a tuple containing:
    *     1. Option[() => Set[String] ] => an optional previous bot message represented as a function that returns
    *                                       a set of strings ( so that everything is dynamic ).
    *     2. List[Either[String, (Regex, Attribute)] ] => the message of the client itself, composed of a list
    *                                       mixed with both parts of a sentence ( " I am " ), but
    *                                       regexes and attributes also ( "[A-z][a-z]*", NameAttr ).
    *   - a response to a particular message:
    *     1. Responses => a set of functions returning a set of strings.
    *
    */

  type Templates = Map[(Option[() => Set[String]], List[Either[String, (Regex, Attribute)]]), Responses]


  type Responses = Set[() => Set[String]]

  /**
    * @param old - previous trie to which new templates are to be added
    * @param acquired - templates to be added
    * @return - a new trie with the acquired templates in memory
    */
  @tailrec
  final def learn(old: Trie, acquired: Templates): Trie =
    acquired.toList match {
      case h :: tail => learn(add(toWords(h._1._2), (h._1._1, h._2), old), tail.toMap)
      case Nil       => old
    }

  /**
    * @param old - previous trie to which new templates are to be added
    * @param acquired - a list of templates to be added
    * @return - a new trie with the list of acquired templates in memory
    */
  def learn(old: Trie, acquired: List[Templates]): Trie = {
    @tailrec
    def startLearning(curr: Trie, toBeLearned: List[Templates]): Trie = {
      toBeLearned match {
        case Nil     => curr
        case h::tail => startLearning(learn(curr, h), tail)
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
  def toWords(message: List[Either[String, (Regex, Attribute)]]): List[Word] =
    message flatMap { w =>
      w match {
        case Left(words)      => words.split(' ').toList.map(w => (w.r, None))
        case Right((r, attr)) => List((r, Some(attr)))
      }
    } filterNot (_._1.toString() == "")
}
