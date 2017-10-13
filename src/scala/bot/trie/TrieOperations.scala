package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  def add(sentence: List[Word], replies: Set[String], trie: Node): Node = {
    def go(curr: Node, words: List[Word]): Node = {
      if (words.isEmpty)
        Node(curr.curr, curr.children, Leaf(curr.leafs.replies ++ replies))
      else {
        curr.children.find(n  => isMatching(n.curr, words.head)) match {
          case None           => go(curr.addValue(createNode(words.head)), words)
          case Some(next)     => Node(curr.curr, curr.children - next ++ Set(go(next, words.tail)), curr.leafs)
        }
      }
    }

    go(trie, sentence)
  }

  def printTrie(trie: Node): Unit = {
    println(trie.curr)
    print("Leafs for current: ")
    trie.leafs.replies.foreach(r => println(r))
    trie.children.foreach(t => printTrie(t))
  }

  private def createNode(word: Word): Node = Node(word, Set[Node]().empty, Leaf(Set[String]().empty))

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
