package scala.bot.handler

import scala.bot.learn.Learner
import scala.bot.trie.Trie
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessages: List[String] = List("")
  var unknownHumanMessages: List[String] = List("Speechless", "I do not know")


  def provideReply(replies: List[String]): String =
    Random.shuffle(replies).head

  def handle(trie: Trie, msg: String): String = {
    val response = search(msg.split(' ').filterNot(_ == "").toList.map(w => (w.r, None)), trie)
    if(response._2.isEmpty)
      provideReply(unknownHumanMessages)
    else{
      currentSessionInformation = currentSessionInformation ++ response._1
      provideResponse(response._2)
    }
  }

  def isDisapproved(brain: Trie, msg: String): String = {
    ""
  }


  def provideResponse(possibleReplies: Set[(Option[String], Set[String])]): String = {
    provideReply(possibleReplies.toList.flatMap(e => e._2.toList))
  }
}
