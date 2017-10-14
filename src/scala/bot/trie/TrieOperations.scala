package scala.bot.trie

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  final def add(words: List[Word], previousBotMessage: Option[String], replies: Set[String], trie: Trie): Trie = {
    def go(curr: Trie, words: List[Word]): Trie = {
      if (words.isEmpty) //went through all the list
        trie // adding the replies to the Set
      else {
        curr.children.find(n => isMatching(n.curr, words.head)) match {
            // current word isn't in the set => add it, and call the function with the same node
          case None => go(curr.addValue(createNode(words.head)), words)
            // next node has been found => remove it from the Set, since its gonna be different
            //it would double stack otherwise
          case Some(next) => Trie(curr.curr, curr.children - next ++ Set(go(next, words.tail)), curr.replies)
        }
      }
    }

    go(trie, words)
  }

  @tailrec
  final def search(words: List[Word], trie: Trie): Set[String] = {
    if (words.isEmpty)
      trie.replies.head._2 //completely ran over all the words
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
          case (wRex, None) => tRex.regex == wRex.regex //both are not attributes
          case (_, Some(_)) => false //word is an attribute, but the node isnt
        }
      case (tRex, Some(tAttr)) =>
        w match {
          case (_, None)           => false //trie node is attribute, but the word isn't
          case (wRex, Some(wAttr)) => //both are attributes
            tRex.pattern.matcher(wRex.regex).matches() && tAttr == wAttr
        }
    }
  }

  private def createNode(word: Word): Trie = Trie(word, Set[Trie]().empty, Set((None, Set[String]().empty)))

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
