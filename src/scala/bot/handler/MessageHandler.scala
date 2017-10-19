package scala.bot.handler

import scala.bot.trie.Trie
import scala.bot.trie.TrieOperations._
import scala.collection.mutable
import scala.util.Random

trait MessageHandler {
  var disapprovalMessages: Set[String] = Set("")
  var unknownHumanMessages: Set[String] = Set("Speechless", "I do not know")

  private val humanLog = new HumanLog()
  private val botLog = new BotLog()
  private var currentSessionInformation: mutable.Map[Attribute, String] = mutable.Map[Attribute, String]()

  def handle(trie: Trie, msg: String): String = {
    val response = search(msg.split(' ').filterNot(_ == "").toList.map(w => (w.r, None)), trie)
    if(response._2.isEmpty){
      val r = provideReply(unknownHumanMessages)
      humanLog.humanLog = humanLog.humanLog :+ msg
      botLog.botLog = botLog.botLog :+ r
      r
    }
    else{
      currentSessionInformation ++= response._1
      val r = provideResponse(response._2)
      humanLog.humanLog = humanLog.humanLog :+ msg
      botLog.botLog = botLog.botLog :+ r
      r
    }
  }

  def isDisapproved(brain: Trie, msg: String): String = {
    ""
  }

  /**
    * First off, all the functions are being applied so that all the replies are known.
    * After that, it is tried to find a match for the last bot message. In that case, it is provided a reply for that
    * specific case. Otherwise, all the possible replies that are dependent on the last message the bot sent are
    * disregarded and all the other are flatMapped and sent as a parameter to a function which will arbitrarily
    * choose a reply
    *
    * @param possibleReplies = a set of optional functions who return a set of string representing the last message
    *                       the bot sent, and a set of functions returning a string representing possible replies.
    * @return a message suitable for the last input the client gave.
    */
  def provideResponse(possibleReplies: Set[(Option[() => Set[String]], Set[() => Set[String]])]): String = {
    val appliedFunctions = possibleReplies map (p => (p._1, p._2.flatMap(e => e())))

    appliedFunctions find (p => p._1.exists(p => p().contains(botLog.botLog.last))) match {
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
