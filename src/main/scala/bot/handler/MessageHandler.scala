package bot.handler

import bot.learn.PossibleReply
import bot.memory.definition.PartOfSentence
import bot.memory.{Attribute, Trie}

import scala.collection.mutable
import scala.util.Random

//TODO this will be an object, and all the variables will come from outside.

trait MessageHandler {
  def disapprovalMessages: Set[String] = Set("")

  def unknownHumanMessages: Set[String] = Set("")

  var currentSessionInformation: mutable.Map[Attribute, String] = mutable.Map[Attribute, String]()

  def handle(trie: Trie,
             msg: String,
             humanLog: List[String],
             botLog: List[String]): String = {
    val response = trie.search(
      msg.split(' ')
        .toList
        .withFilter(_ != "")
        .map(w => PartOfSentence(w.r, None)))

    if (response.possibleReplies.isEmpty) {
      val r = provideReply(unknownHumanMessages)
      r
    }
    else {
      currentSessionInformation ++= response.attributesFound
      val r = provideResponse(response.possibleReplies, botLog.lastOption match {
        case Some(last) => last
        case None => ""
      })
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
    *                        the bot sent, and a set of functions returning a string representing possible replies.
    * @return a message suitable for the last input the client gave.
    */
  private def provideResponse(possibleReplies: Set[PossibleReply],
                              lastBotMsg: String): String = {


    val appliedFunctions = possibleReplies map AppliedFunctions.toAppliedFunctions

    appliedFunctions find (_.isAnswerToPreviousBotMessage(lastBotMsg)) match {
      case None =>
        provideReply(appliedFunctions
          .withFilter{_.hasNoPreviousBotMessage}
          .flatMap{e => e.appliedFunctions})
      case Some(reply) =>
        provideReply(reply.appliedFunctions)
    }
  }

  def getAttribute(attribute: Attribute): Option[String] =
    currentSessionInformation.get(attribute)

  private def provideReply(replies: Set[String]): String = {
    if (replies.isEmpty)
      provideReply(unknownHumanMessages)
    else
      Random.shuffle(replies).head
  }
}
