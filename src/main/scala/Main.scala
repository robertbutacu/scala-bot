import bot.memory.Trie
import bot.memory.definition.NodeUserInformation
import example.brain.Manager

object Main extends App with Manager {
  //clusterizedMasterBrain
  implicit class X(trie: Trie) {
    def bla = "abc"
  }

  val trie = Trie(NodeUserInformation())

  print(trie.bla)
}
