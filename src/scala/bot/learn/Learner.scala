package scala.bot.learn

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.bot.trie.TrieOperations
import scala.util.matching.Regex

trait Learner extends TrieOperations{
  type Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]
  type Responses = List[String]

  def learn(old: Templates , acquired: Templates): Templates =
    old ++ acquired

  def learn(old: Templates, acquired: List[Templates]): Templates = {
    @tailrec
    def startLearning(curr: Templates, toBeLearned: List[Templates]): Templates = {
      toBeLearned match {
        case h :: tail => startLearning(learn(curr, h), tail)
        case Nil       => curr
      }
    }

    startLearning(old, acquired)
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
    message flatMap{w =>
    w match {
      case Left(words) => words.split(' ').toList.map(w => (w.r, None))
      case Right((r, attr)) => List((r, Some(attr)))
    }} filterNot ( _._1.toString() == "")
}
