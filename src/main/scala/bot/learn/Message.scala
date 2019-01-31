package bot.learn

import bot.connections.Attribute
import bot.learn.RepliesLearner.Responses
import bot.types.BotMessage

import scala.util.matching.Regex

case class Message(pattern: Regex, attribute: Option[Attribute])


case class SearchResponses(attributesFound: Map[Attribute, String],
                           possibleReplies: Set[PossibleReply] = Set().empty)


case class PossibleReply(previousBotMessage: Option[BotMessage],
                         possibleReply: Responses)

object PossibleReply {
  def apply(reply: MessageTemplate): PossibleReply = {
    PossibleReply(reply.humanMessage.previousBotReply, reply.botReplies)
  }
}
