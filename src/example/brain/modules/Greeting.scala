package example.brain.modules

import scala.bot.handler.Attribute
import scala.bot.learn.Learner
import scala.util.matching.Regex

trait Greeting extends Learner {
  val greetings: Templates = Map[((Option[String]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Hi"))) -> Set("Hello", "What's up", "Im a bot"),
      (None, List(Left("Test"))) -> Set("How old are you?"),
      (None, List(Left("Greetings"))) -> Set("Greetings", "Hello", "Sup")
    )
}
