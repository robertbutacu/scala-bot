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
        var updatedCurr = curr
        curr.children.find(n  => isMatching(n.curr, words.head)) match {
          case None           => /*println("adding " + words.head);*/ updatedCurr = go(curr.addValue(createNode(words.head)), words)
          case Some(next)     => /*println("going to " + next.curr);*/ updatedCurr = Node(curr.curr, curr.children - next ++ Set(go(next, words.tail)), curr.leafs)
        }
        updatedCurr
      }
    }

    go(trie, sentence)
  }

  def printTrie(trie: Node): Unit = {
    println(trie.curr)
    trie.children.foreach(t => printTrie(t))
  }

  private def createNode(word: Word): Node = Node(word, Set[Node]().empty, Leaf(Set[String]().empty))

  private def isMatching(node: Word, that: Word): Boolean ={
    println("comparing " + node._1 + " to " + that._1 + " and " + node._2 + " " + that._2 +
    " and returning " + (node._1.regex == that._1.regex && node._2 == that._2))

    node._1.regex == that._1.regex && node._2 == that._2
  }
}
