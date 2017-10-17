package scala.bot.handler

import scala.bot.learn.Learner
import scala.bot.trie.Trie
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessages: Set[String] = Set("")
  var unknownHumanMessages: Set[String] = Set("Speechless", "I do not know")


  def provideReply(replies: Set[String]): String =
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

  def provideResponse(possibleReplies: Set[(Option[String], Set[() => Set[String]])]): String = {
    val appliedFunctions = possibleReplies map (p => (p._1, p._2 flatMap(e => e())))
    appliedFunctions find (p => p._1.contains(BotLog.botLog.last)) match {
      case None        => provideReply(appliedFunctions flatMap ( e => e._2))
      case Some(reply) => provideReply(reply._2)
    }
  }
}
