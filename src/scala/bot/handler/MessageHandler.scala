package scala.bot.handler

import scala.bot.learn.Learner
import scala.bot.trie.Trie
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessage: List[String] = List("")
  var unknownHumanMessage: List[String] = List("Speechless", "I do not know")
  var currentSessionInformation: Map[Attribute, String] = Map[Attribute, String]().empty


  def provideReply(replies: List[String]): String =
    Random.shuffle(replies).head

  def handle(trie: Trie, msg: String): String = {
    provideResponse(search(msg.split(' ').filterNot(_ == "").toList.map(w => (w.r, None)), trie), msg)
  }

  def isDisapproved(brain: Trie, msg: String): String = {
    ""
  }


  def provideResponse(possibleReplies: Set[(Option[String], Set[String])], message: String): String = {
    provideReply(possibleReplies.toList.flatMap(e => e._2.toList))
  }
}
