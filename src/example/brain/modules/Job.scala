package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.handler.Characteristic
import scala.bot.learn.RepliesLearner.{Responses, Templates}
import scala.util.matching.Regex

trait Job extends BrainFunctions {
  val jobs: Templates = Map[((Option[() => Set[String]]), List[Either[String, (Regex, Characteristic)]]), Responses](
      (None, List(Left("I'm a programmer"))) -> Set(passionReply _),
      (None, List(Left("I dont have a job"))) -> Set(ageReply _)
    )
}
