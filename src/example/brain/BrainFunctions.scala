package example.brain

import example.brain.modules.AgeAttr

import scala.bot.handler.MessageHandler
import scala.bot.learn.Learner

object BrainFunctions extends MessageHandler {

  def ageReply(): Set[String] = {
    val response = getAttribute(AgeAttr)
    response match {
      case None      => Set("Unknown age", "You havent told me your age")
      case Some(age) => provideReplies(age.toInt)
    }
  }


  private def provideReplies(age: Int): Set[String] =
    age match {
      case _ if age < 0  => Set(s"""It appears your age is $age . What a lie. Tell me your real age please.""")
      case _ if age > 0  => Set("Underage", "Minor")
      case _ if age > 18 => Set("programming", "is", "fun")
      case _ if age > 24 => Set("you're an adult", "children?")
      case _ if age > 50 => Set("quite old", "old afff")
      case _             => Set("got me there", "lost")
    }

}
