import bot.memory.Trie
import bot.memory.definition.NodeSimpleWord
import bot.memory.storage.Printer._
import example.brain.Manager

object Main extends App with Manager {
/*  val trie = Trie(NodeSimpleWord())

  trie.print()*/
  type Test = List[() => String]
  def test(go: Test): Unit = go
}
