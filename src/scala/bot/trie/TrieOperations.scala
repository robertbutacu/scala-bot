package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  def add(sentence: List[Word], possibleReplies: Set[String], trie: Node): Node = {
    def go(curr: Node, remainingSentence: List[Word]): Node = {
      if (remainingSentence.isEmpty)
        Node(curr.current, curr.children, Leaf(curr.leafs.possibleReplies ++ possibleReplies))
      else {
        var updatedCurr = curr
        curr.children.find(n => isMatching(n.current, sentence.head)) match {
          case None           => println("Adding " + sentence.head); updatedCurr = go(curr.addValue(createNode(sentence.head)), remainingSentence)
          case Some(next)     => go(next, remainingSentence.tail)
        }
        updatedCurr
      }
    }

    go(trie, sentence)
  }

  private def createNode(word: Word): Node = Node(word, Set[Node]().empty, Leaf(Set[String]().empty))

  private def isMatching(node: Word, partOfSentence: Word): Boolean =
    node._1 == partOfSentence._1 && node._2 == partOfSentence._2
}
