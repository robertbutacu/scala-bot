package scala.bot.handler

import scala.bot.learn.Learner
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessage: List[String] = List("")
  var unknownHumanMessage: List[String] = List("Speechless", "I do not know")


  def provideReply(replies: List[String]): String =
    Random.shuffle(replies).head

  def handle(brain: Templates, msg: String): String = {
    ""
  }

  def isDisapproved(brain: Templates, msg: String): String = {
    ""
  }


  def provideResponse(possibleReplies: Templates, message: String): String = {
    ""
  }
}
