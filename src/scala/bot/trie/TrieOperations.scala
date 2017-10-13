package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  def add(sentence: List[Word], replies: Set[String], trie: Trie): Trie = {
    def go(curr: Trie, words: List[Word]): Trie = {
      if (words.isEmpty)
        Trie(curr.curr, curr.children, curr.replies ++ replies)
      else {
        curr.children.find(n  => isMatching(n.curr, words.head)) match {
          case None           => go(curr.addValue(createNode(words.head)), words)
          case Some(next)     => Trie(curr.curr, curr.children - next ++ Set(go(next, words.tail)), curr.replies)
        }
      }
    }

    go(trie, sentence)
  }

  def printTrie(trie: Trie): Unit = {
    println("Node " + trie.curr + "   ")
    trie.replies.foreach(r => println("Leaf " + r))
    trie.children.foreach(t => printTrie(t))
  }

  private def createNode(word: Word): Trie = Trie(word, Set[Trie]().empty, Set[String]().empty)

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
