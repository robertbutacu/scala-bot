package scala.bot

import scala.util.Random

trait MessageHandler extends Learner {
  def handle(brain: Templates, msg: String): String = {
    def provideReply(replies: List[String]): String =
      Random.shuffle(replies).head

    brain.get((None, msg)) match {
      case None          => "I'm sorry, but I hold no information about that"
      case Some(replies) => provideReply(replies)
    }
  }
}
