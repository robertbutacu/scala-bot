package scala.bot.trie

import scala.bot.learn.RepliesLearner.Responses
import scala.util.matching.Regex

case class PartOfMessage(part: Either[String, (Regex, Attribute)])

case class HumanMessage(previousBotReply: Option[() => Set[String]],
                        message: List[PartOfMessage])

case class Reply(humanMessage: HumanMessage,
                 botReplies: Responses)
