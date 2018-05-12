package bot.learn

case class PossibleReply(previousBotMessage: Option[() => Set[String]],
                         possibleReply: Set[() => Set[String]])

object PossibleReply {
  def apply(reply: Reply): PossibleReply = {
    PossibleReply(reply.humanMessage.previousBotReply, reply.botReplies)
  }
}