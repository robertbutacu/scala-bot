package bot.handler

import bot.connections.Attribute
import bot.handler.Source.{Bot, Human}
import bot.memory.Trie

case class SessionInformation(brain:                Trie,
                              unknownMessages:      Set[String]            = Set(""),
                              disapprovalMessages:  Set[String]            = Set(""),
                              knownCharacteristics: Map[Attribute, String] = Map.empty,
                              log:                  List[ConversationLine] = List.empty) {
  def lastHumanMessage: Option[ConversationLine] = log.find(_.source == Human)
  def lastBotMessage:   Option[ConversationLine] = log.find(_.source == Bot)

  def addBotMessage(message: String): SessionInformation = {
    this.copy(log = this.log :+ ConversationLine(message, Bot))
  }

  def addHumanMessage(message: String): SessionInformation = {
    this.copy(log = this.log :+ ConversationLine(message, Human))
  }
}

case class ConversationLine(message: String, source: Source)

sealed trait Source

object Source {
  case object Bot   extends Source
  case object Human extends Source
}
