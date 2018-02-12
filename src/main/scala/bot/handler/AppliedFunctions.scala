package bot.handler

import bot.learn.PossibleReply

protected[this] case class AppliedFunctions(previousBotMsgs: Set[String],
                                            appliedFunctions: Set[String]){
  def isAnswerToPreviousBotMessage(previousBotMessage: String): Boolean =
    previousBotMsgs.contains(previousBotMessage)

  def hasNoPreviousBotMessage: Boolean = previousBotMsgs.isEmpty
}

object AppliedFunctions {
  def toAppliedFunctions(p: PossibleReply) =
    AppliedFunctions(p.previousBotMessage.map(_()).toSet.flatten, p.possibleReply.flatMap(_ ()))
}