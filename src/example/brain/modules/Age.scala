package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Age extends Learner with BrainFunctions {
  val ages: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Im "),
        Right("[0-9]+".r, AgeAttr),
        Left(" years old"))) -> Set(ageReply _),
      (None, List(Left("Im passionate about"),
        Right("[a-zA-Z]+".r, PassionAttr))) -> Set(passionReply _)
    )
}
