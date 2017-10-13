package scala.bot.trie

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  final def add(sentence: List[Word], replies: Set[String], trie: Trie): Trie = {
    def go(curr: Trie, words: List[Word]): Trie = {
      if (words.isEmpty)
        Trie(curr.curr, curr.children, curr.replies ++ replies)
      else {
        curr.children.find(n => isMatching(n.curr, words.head)) match {
          case None => go(curr.addValue(createNode(words.head)), words)
          case Some(next) => Trie(curr.curr, curr.children - next ++ Set(go(next, words.tail)), curr.replies)
        }
      }
    }

    go(trie, sentence)
  }

  @tailrec
  final def search(words: List[Word], trie: Trie): Set[String] = {
    if (words.isEmpty)
      trie.replies //completely ran over all the words
    else {
      val next = trie.children.find(t => isMatching(t, words.head))
      next match {
        case None            => Set() //word wasn't found in the trie
        case Some(otherTrie) => search(words.tail, otherTrie) //going deeper
      }
    }
  }

  def printTrie(trie: Trie): Unit = {
    println("Node " + trie.curr + "   ")
    trie.replies.foreach(r => println("Leaf " + r))
    trie.children.foreach(t => printTrie(t))
  }

  private def isMatching(t: Trie, w: Word): Boolean = {
    t.curr match {
      case (tRex, None)        =>
        w match {
          case (wRex, None) => tRex.regex == wRex.regex
          case (_, Some(_)) => false
        }
      case (tRex, Some(tAttr)) =>
        w match {
          case (_, None)           => false
          case (wRex, Some(wAttr)) =>
            tRex.pattern.matcher(wRex.regex).matches() && tAttr == wAttr
        }
    }
  }

  private def createNode(word: Word): Trie = Trie(word, Set[Trie]().empty, Set[String]().empty)

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
