package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  def add(sentence: List[Word], possibleReplies: List[String], trie: Node): Node = {
    def go(curr: Node, remainingSentence: List[Word]): Node = {
      if (remainingSentence.isEmpty)
        curr
      else {
        curr.children.find(n => isMatching(n.current, sentence.head)) match {
          case None        => go(Node(curr.current,
            curr.children ++ Set(Node(remainingSentence.head, Set[Node]().empty, Set[Leaf]().empty)),
            curr.leafs), remainingSentence.tail)
          case Some(_) => curr
        }
      }
    }

    go(trie, sentence)
  }

  private def isMatching(node: Word, partOfSentence: Word): Boolean =
    node._1 == partOfSentence._1 && node._2 == partOfSentence._2
}
