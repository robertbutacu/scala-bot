package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.RepliesLearner.{Responses, Templates}
import scala.bot.trie.Attribute
import scala.util.matching.Regex

trait Greeting extends BrainFunctions {
  val greetings: Templates = Map[((Option[() => Set[String]]), List[Either[String, (Regex, Attribute)]]), Responses](
      (None, List(Left("Hi"))) -> Set(ageReply _),
      (None, List(Left("Test"))) -> Set(ageReply _),
      (None, List(Left("Greetings"))) -> Set(ageReply _)
    )
}
