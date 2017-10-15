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

  def toWords(message: List[Either[String, (Regex, Attribute)]]): List[Word] = {
    message flatMap{w =>
    w match {
      case Left(words) => words.split(' ').toList.map(w => (w.r, None))
      case Right((r, attr)) => List((r, Some(attr)))
    }} filterNot ( _._1.toString() == "")
  }
}
