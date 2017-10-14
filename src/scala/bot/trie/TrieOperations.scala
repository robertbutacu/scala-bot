package scala.bot.trie

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  /**
    * For a current Trie, the algorithm returns another trie with the message added.
    *
    * @param words - list of words to be added into the trie
    * @param replies - possible replies to the current message
    * @param trie    - trie where the message will be stored
    * @return
    */
  final def add(words: List[Word], replies: (Option[String], Set[String]), trie: Trie): Trie = {
    def go(curr: Trie, words: List[Word]): Trie = {
      if (words.isEmpty) //went through all the list
        addReplies(curr, replies) // adding the replies to the Set
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

  /**
    * The algorithm describes the search of a message in a trie, by parsing every word and matching it,
    * thus returning a Set of possible replies depending on bot's previous replies.
    *
    * @param words - the sentence that is to be found, or not
    * @param trie  - the trie in which it will be searched
    * @return      - returns a Set of (previousMessageFromBot, Set[possible replies]),
    *              from which another algorithm will pick the best choice.
    */
  @tailrec
  final def search(words: List[Word], trie: Trie): Set[(Option[String], Set[String])] = {
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

  /**
    *
    * @param t - trie on which is operated
    * @param replies - replies that are to be added
    * @return - new leafs which also contain the new replies
    *         There are 2 cases:
    *             1. when they depend on a previous bot message ( or lack of) also stored:
    *                 the replies are appended to the already existing replies.
    *             2. when they aren't stored at all:
    *                 they are registered as new replies with their attribute.
    */
  private def addReplies(t: Trie, replies: (Option[String], Set[String])): Trie =
    t.replies.find(l => l._1 == replies._1) match {
      case None      => Trie(t.curr, t.children, t.replies ++ Set(replies))
      case Some(rep) => Trie(t.curr, t.children, t.replies -- Set(rep) ++ Set((rep._1, rep._2 ++ replies._2)))
    }

  /**
    *There are 4 cases:
    *   1. for when t is not a regex ( plain message):
    *       a. word is also a message => check for equality
    *       b. word is an attribute        => they don't match
    *   2. t is a regex:
    *       a. word is not an attribute => they don't match
    *       b. word is an attribute     => check if it matches the regex
    * @param t - trie word
    * @param w - message word
    * @return
    */
  private def isMatching(t: Trie, w: Word): Boolean = {
    t.curr match {
      case (tRex, None)        =>
        w match {
          case (wRex, None) => tRex.regex == wRex.regex //neither are attributes
          case (_, Some(_)) => false //word is an attribute, but the node isn't
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
