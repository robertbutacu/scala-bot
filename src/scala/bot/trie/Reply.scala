package scala.bot.trie

import scala.bot.learn.RepliesLearner.Responses
import scala.util.matching.Regex

/**
  * @param previousBotReply - an optional function that returns a set of strings =>
  *                             possible previous messages from the bot
  * @param message          - human's actual message, composed of multiple words/regexes.
  */

//TODO change message so its a list of tuples
// Either used to carry a possible message, bad design
case class HumanMessage(previousBotReply: Option[() => Set[String]],
                        message: List[Either[String, (Regex, Attribute)]])


/**
  *
  * @param humanMessage - last message sent by the human, restricted by the last message sent by the bot possibly.
  * @param botReplies   - a set of functions which return a string
  */
case class Reply(humanMessage: HumanMessage,
                 botReplies: Responses)
