package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Greeting extends Learner {
  val greetings: Templates = learn(Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses]().empty,
    Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Hi"))) -> List("Hello", "What's up", "Im a bot"),
      (None, List(Left("Test"))) -> List("How old are you?"),
      (None, List(Left("Greetings"))) -> List("Greetings", "Hello", "Sup")
    ))
}
