package bot.handler

import bot.connections.Attribute
import bot.memory.Trie

case class SessionInformation(replies: Trie,
                              unknownMessages: Trie,
                              disapprovalMessages: Trie,
                              knownCharacteristics: Map[Attribute, String],
                              log: List[ConversationLine])

case class ConversationLine(message: String, source: Source)

sealed trait Source

object Source {
  case object Bot   extends Source
  case object Human extends Source
}
