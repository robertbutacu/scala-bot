package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Age extends Learner {
  val ages: Templates = learn(Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]().empty,
    Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Im "),
        Right("[0-9]+".r, AgeAttr),
        Left(" years old"))) -> List("Young", "Im 0 yo", "Im a bot"),
      (None, List(Left("Im 40 years old"))) -> List("Old boiii", "CAN YOU HEAR ME?", "I love retro"),
      (Some("How old are you?"), List(Left("Im 30 years old"))) -> List("It works")
    ))
}
