package example.brain.modules

import example.brain.BrainFunctions._

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Age extends Learner{
  val ages: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Im "),
        Right("[0-9]+".r, AgeAttr),
        Left(" years old"))) -> replies(ageReply, Some(AgeAttr)),
      (Some("How old are you?"), List(Left("Im 30 years old"))) -> Set("It works")
    )
}
