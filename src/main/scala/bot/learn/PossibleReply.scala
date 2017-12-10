package bot.learn

class PossibleReply(val previousBotMessage: Option[() => Set[String]],
                    val possibleReply: Set[() => Set[String]]) {
}

object PossibleReply {
  def apply(previousBotMessage: Option[() => Set[String]],
            possibleReply: Set[() => Set[String]]): PossibleReply =
    new PossibleReply(previousBotMessage, possibleReply)
}
