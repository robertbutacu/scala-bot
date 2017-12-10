package bot.learn

case class PossibleReply(previousBotMessage: Option[() => Set[String]],
                         possibleReply: Set[() => Set[String]])