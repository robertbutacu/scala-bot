package bot.handler

import bot.learn.PossibleReply

protected[this] case class AppliedFunctions(previousBotMsg: Option[() => Set[String]],
                                            appliedFunctions: Set[String])

object AppliedFunctions {
  def toAppliedFunctions(p: PossibleReply) =
    AppliedFunctions(p.previousBotMessage, p.possibleReply.flatMap(_ ()))
}