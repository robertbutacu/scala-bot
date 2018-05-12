import bot.memory.Trie
import bot.memory.definition.{NodeSimpleWord, NodeUserInformation}
import example.brain.Manager
import bot.memory.storage.Printer._

object Main extends App with Manager {
  val trie = Trie(NodeSimpleWord())

  trie.print()
}
