package example.brain.modules

import example.brain.BrainFunctions

import scala.bot.learn.RepliesLearner.Responses
import scala.bot.trie.{Attribute, HumanMessage, PartOfMessage, Reply}
import scala.util.matching.Regex

/*
case class Reply(humanMessage: HumanMessage,
                 botReplies: Responses)

case class HumanMessage(previousBotReply: Option[() => Set[String]],
                        message: List[PartOfMessage])

case class PartOfMessage(part: Either[String, (Regex, Attribute)])

 */

trait Age extends BrainFunctions with Attributes{
  val ages: List[Reply] = List(
    Reply(HumanMessage(None, List(PartOfMessage(Left("Im ")),
      PartOfMessage(Right("[0-9]+".r, age)),
      PartOfMessage(Left(" years old")))), Set(ageReply _)),
    Reply(HumanMessage(None, List(PartOfMessage(Left("Im passionate about")),
      PartOfMessage(Right("[a-zA-Z]+".r, passion)))),
      Set(passionReply _)),
    Reply(HumanMessage(Some(passionReply _),
      List(PartOfMessage(Left("What am i passionate about")))),
      Set(passionReplies _))
  )
}
