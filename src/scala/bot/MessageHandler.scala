package scala.bot

import scala.util.Random

trait MessageHandler extends Learner {
  def handle(brain: Map[String, List[String]], msg: String): String = {
    def provideReply(replies: List[String]): String =
      Random.shuffle(replies).head

    brain.get(msg) match {
      case None          => "I'm sorry, but I hold no information about that"
      case Some(replies) => provideReply(replies)
    }
  }
}
