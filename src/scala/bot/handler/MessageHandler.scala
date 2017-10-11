package scala.bot.handler

import scala.bot.learn.Learner
import scala.util.Random

trait MessageHandler extends Learner {

  def handle(brain: Templates, msg: String): String = {
    def provideReply(replies: List[String]): String =
      Random.shuffle(replies).head

    //at this point,
    brain.get((None, msg)) match {
      case None          => "I'm sorry, but I hold no information about that"
      case Some(replies) => provideReply(replies)
    }
  }
}
