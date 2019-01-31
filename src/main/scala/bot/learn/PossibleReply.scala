package bot.learn

import bot.learn.RepliesLearner.Responses
import bot.types.BotMessage

case class PossibleReply(previousBotMessage: Option[BotMessage],
                         possibleReply: Responses)

object PossibleReply {
  def apply(reply: MessageTemplate): PossibleReply = {
    PossibleReply(reply.humanMessage.previousBotReply, reply.botReplies)
  }
}