package bot.learn

import bot.learn.RepliesLearner.Responses
import bot.memory.Attribute

import scala.util.matching.Regex

/**
  * @param previousBotReply - an optional function that returns a set of strings =>
  *                             possible previous messages from the bot
  * @param message          - human's actual message, composed of multiple words/regexes.
  */
case class HumanMessage(previousBotReply: Option[() => Set[String]],
                        message: List[Message])


/**
  *
  * @param humanMessage - last message sent by the human, restricted by the last message sent by the bot possibly.
  * @param botReplies   - a set of functions which return a string
  */
case class MessageTemplate(humanMessage: HumanMessage,
                           botReplies: Responses)
