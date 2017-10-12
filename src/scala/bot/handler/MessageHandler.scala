package scala.bot.handler

import scala.bot.learn.Learner
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessage: List[String] = List("")
  var unknownHumanMessage: List[String] = List("Speechless", "I do not know")

  def provideReply(replies: List[String]): String =
    Random.shuffle(replies).head

  def handle(brain: Templates, msg: String): String = {
    val possibleReplies = brain.filterKeys(k => k._2 == msg)
    val response = provideResponse(possibleReplies, msg)

    val disapproved = isDisapproved(brain, msg)
    BotLog.botLog = BotLog.botLog ++ List(response)
    HumanLog.humanLog = HumanLog.humanLog ++ List(msg)

    disapproved ++ response
  }

  /**
    * In order to return a disapproval message, gotta scan for the last message and then look it up in the brain.
    * If i get it and the client message is different, then behold the disapproval message!
    *
    */
  def isDisapproved(brain: Templates, msg: String): String = {
    if(brain.exists(k => k._1._1.contains(BotLog.botLog.last) && k._1._2 != msg))
      provideReply(disapprovalMessage)
    else
      ""
  }


  /***
    *
    * @param possibleReplies - contains all the possible replies for the current message, disregarding the previous
    *                        message from the bot.
    * @param message - client's message
    * @return - a proper response to current message. First , it's trying to match to a message with a previous
    *         message from the bot. If that fails, it tries to match a message without a previous bot msg.
    */
  def provideResponse(possibleReplies: Templates, message: String): String = {
    val lastBotMessage = BotLog.botLog.last
    val responseWithHistory = (Some(lastBotMessage), message)

    possibleReplies.get(responseWithHistory) match {
      //when previous bot message doesn't match
      case None          => possibleReplies.get((None, message)) match {
        case None          => provideReply(unknownHumanMessage) //nothing matches
        case Some(replies) => provideReply(replies) // found match with no previous message
      }
      case Some(replies) => provideReply(replies) // found match for previous bot message
    }
  }
}
