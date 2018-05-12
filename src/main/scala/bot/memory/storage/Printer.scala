package bot.memory.storage

import bot.memory.Trie

trait Printer {
  def print(): Unit
}

object Printer {
  implicit class TriePrinter(trie: Trie) extends Printer {
    override def print(): Unit = {
      def go(trie: Trie, tabs: Int): Unit = {
        println("\t" * tabs + "Node " + trie.information + "   ")
        trie.replies.foreach(r => println("\t" * (tabs + 1) + " Leaf " + r))
        trie.children.foreach(t => go(t, tabs + 1))
      }

      go(trie, 0)
    }
  }
}
