package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Job extends Learner with BrainFunctions {
  val jobs: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("I'm a programmer"))) -> Set(passionReply _),
      (None, List(Left("I dont have a job"))) -> Set(ageReply _)
    )
}
