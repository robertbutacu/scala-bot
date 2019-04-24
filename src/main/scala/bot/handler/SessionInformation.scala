package bot.handler

import bot.connections.Attribute
import bot.handler.Source.{Bot, Human}
import bot.memory.Trie

case class SessionInformation(replies:              Trie,
                              unknownMessages:      Trie,
                              disapprovalMessages:  Trie,
                              knownCharacteristics: Map[Attribute, String],
                              log:                  List[ConversationLine]) {
  def lastHumanMessage: Option[ConversationLine] = log.find(_.source == Human)
  def lastBotMessage:   Option[ConversationLine] = log.find(_.source == Bot)
}

case class ConversationLine(message: String, source: Source)

sealed trait Source

object Source {
  case object Bot   extends Source
  case object Human extends Source
}
