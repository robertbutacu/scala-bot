package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex
import example.brain.BrainFunctions._


trait Job extends Learner {
  val jobs: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("I'm a programmer"))) -> Set(ageReply _),
      (None, List(Left("I dont have a job"))) -> Set(ageReply _)
    )
}
