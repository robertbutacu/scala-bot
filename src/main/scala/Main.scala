import bot.memory.Trie
import bot.memory.definition.NodeUserInformation
import example.brain.Manager
import bot.memory.storage.Printer.TriePrinter

object Main extends App with Manager {
  val trie = Trie(NodeUserInformation())

  trie.print()
}
