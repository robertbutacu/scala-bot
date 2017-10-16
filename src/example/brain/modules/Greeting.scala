package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex
import example.brain.BrainFunctions._


trait Greeting extends Learner {
  val greetings: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Hi"))) -> Set(ageReply _),
      (None, List(Left("Test"))) -> Set(ageReply _),
      (None, List(Left("Greetings"))) -> Set(ageReply _)
    )
}
