package bot.memory.storage

import bot.memory.Trie

trait Printer[T] {
  def print(t: T): Unit
}

object Printer {
  implicit def triePrinter: Printer[Trie] = (t: Trie) => {
    def go(trie: Trie, tabs: Int): Unit = {
      println("\t" * tabs + "Node " + trie.information + "   ")
      trie.replies.foreach(r => println("\t" * (tabs + 1) + " Leaf " + r))
      trie.children.foreach(t => go(t, tabs + 1))
    }

    go(t, 0)
  }
}
