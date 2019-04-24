package bot.handler

import bot.learn.PossibleReply

/**
  *  Since the possible replies and previous messages are all functions which return strings,
  *  there was a need for a data which hold the actual results
  *  Slight optimisation for cases where the function is expensive, otherwise there shouldn't make any difference.
  * @param previousBotMsgs - ???
  * @param appliedFunctions - ???
  */
protected[this] case class AppliedFunctions(previousBotMsgs:  Set[String],
                                            appliedFunctions: Set[String]){
  def isAnswerToPreviousBotMessage(previousBotMessage: String): Boolean =
    previousBotMsgs.contains(previousBotMessage)

  def hasNoPreviousBotMessage: Boolean = previousBotMsgs.isEmpty
}

object AppliedFunctions {
  def toAppliedFunctions(p: PossibleReply) =
    AppliedFunctions(p.previousBotMessage.map(_()).toSet.flatten, p.possibleReply.flatMap(_()))
}