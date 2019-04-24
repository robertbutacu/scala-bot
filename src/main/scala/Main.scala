import bot.memory.Trie
import bot.memory.definition.NodeSimpleWord
import bot.memory.storage.Printer._
import cats.Id
import example.Bot
import example.brain.Manager
import cats.instances.all._

object Main extends App with Manager {
  val trie = Trie(NodeSimpleWord())

  trie.print()

  val bot = Bot[Id]()

  bot.startDemo
}
