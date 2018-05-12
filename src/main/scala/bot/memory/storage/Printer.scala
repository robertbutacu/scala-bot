package bot.memory.storage

import bot.learn.PossibleReply
import bot.memory.Trie
import bot.memory.definition.NodeInformation

trait Printer {
  def information: NodeInformation
  def children: Set[Trie]
  def replies: Set[PossibleReply]

  def print(): Unit
}
