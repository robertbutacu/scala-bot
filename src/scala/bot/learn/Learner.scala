package scala.bot.learn

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait Learner {
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
}
