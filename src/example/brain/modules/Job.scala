package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Job extends Learner {
  val jobs: Templates = learn(Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]().empty,
    Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("I'm a programmer"))) -> List("Me too", "I've been created using Scala", "Thats awesome"),
      (None, List(Left("I dont have a job"))) -> List("Broke :/", "Are you searching for a job?", "its okay...")
    ))
}
