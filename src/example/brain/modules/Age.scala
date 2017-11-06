package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.RepliesLearner.{Responses, Templates}
import scala.bot.trie.Attribute
import scala.util.matching.Regex

trait Age extends BrainFunctions with Attributes{
  val ages: Templates = Map[(Option[() => Set[String]], List[Either[String, (Regex, Attribute)]]), Responses](
    (None, List(Left("Im "),
      Right("[0-9]+".r, age),
      Left(" years old"))) -> Set(ageReply _),
    (None, List(Left("Im passionate about"),
      Right("[a-zA-Z]+".r, passion))) -> Set(passionReply _),
    (Some(passionReply _), List(Left("What am i passionate about"))) -> Set(passionReplies _)
  )
}
