package scala.bot.handler

import scala.bot.learn.Learner
import scala.bot.trie.Trie
import scala.collection.mutable
import scala.util.Random

trait MessageHandler extends Learner {
  var disapprovalMessages: Set[String] = Set("")
  var unknownHumanMessages: Set[String] = Set("Speechless", "I do not know")

  var currentSessionInformation: mutable.Map[Attribute, String] = mutable.Map[Attribute, String]()

  def handle(trie: Trie, msg: String): String = {
    val response = search(msg.split(' ').filterNot(_ == "").toList.map(w => (w.r, None)), trie)
    if(response._2.isEmpty){
      val r = provideReply(unknownHumanMessages)
      HumanLog.humanLog = HumanLog.humanLog :+ msg
      BotLog.botLog = BotLog.botLog :+ r
      r
    }
    else{
      currentSessionInformation ++= response._1
      val r = provideResponse(response._2)
      HumanLog.humanLog = HumanLog.humanLog :+ msg
      BotLog.botLog = BotLog.botLog :+ r
      r
    }
  }

  def isDisapproved(brain: Trie, msg: String): String = {
    ""
  }

  def provideResponse(possibleReplies: Set[(Option[String], Set[() => Set[String]])]): String = {
    val appliedFunctions = possibleReplies map (p => (p._1, p._2.flatMap(e => e())))

    appliedFunctions find (p => p._1.contains(BotLog.botLog.last)) match {
      case None        => provideReply(appliedFunctions filter (_._1.isEmpty) flatMap ( e => e._2))
      case Some(reply) => provideReply(reply._2)
    }
  }

  def getAttribute(attribute: Attribute): Option[String] =
    currentSessionInformation.get(attribute)

  def provideReply(replies: Set[String]): String ={
    if(replies.isEmpty)
      provideReply(unknownHumanMessages)
    else
      Random.shuffle(replies).head
  }
}
