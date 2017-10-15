package scala.bot.learn

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.bot.trie.{Trie, TrieOperations}
import scala.util.matching.Regex

trait Learner extends TrieOperations {
  type Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]
  type Responses = Set[String]

  @tailrec
  final def learn(old: Trie, acquired: Templates): Trie =
    acquired.toList match {
      case h :: tail => learn(add(toWords(h._1._2), (h._1._1, h._2), old), tail.toMap)
      case Nil       => old
    }


  def learn(old: Templates, acquired: List[Templates]): Trie = {
    //@tailrec
    def startLearning(curr: Trie, toBeLearned: List[Templates]): Unit = {

    }

    //startLearning(old, acquired)
    Trie()
  }

  /**
    * The anonymous function creates a List of Lists of (Regex, Some(Attribute)),
    * which will be flattened. The list is then filtered to not contain any
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
