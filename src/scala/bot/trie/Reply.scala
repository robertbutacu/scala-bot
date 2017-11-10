package scala.bot.trie

import scala.bot.learn.RepliesLearner.Responses
import scala.util.matching.Regex


/**
  * Represents a string or a regex (with the appended attribute) from a sentence(message).
 */
case class PartOfMessage(part: Either[String, (Regex, Attribute)])


/**
  * @param previousBotReply - an optional function that returns a set of strings =>
  *                             possible previous messages from the bot
  * @param message          - human's actual message, composed of multiple words/regexes.
  */
case class HumanMessage(previousBotReply: Option[() => Set[String]],
                        message: List[PartOfMessage])


/**
  *
  * @param humanMessage - last message sent by the human, restricted by the last message sent by the bot possibly.
  * @param botReplies   - a set of functions which return a string
  */
case class Reply(humanMessage: HumanMessage,
                 botReplies: Responses)
