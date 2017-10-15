package scala.bot.trie

import scala.annotation.tailrec
import scala.bot.handler.Attribute
import scala.util.matching.Regex

trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  /**
    * For a current Trie, the algorithm returns another trie with the message added.
    *
    * @param message - list of words to be added into the trie
    * @param replies - possible replies to the current message
    * @param trie    - trie where the message will be stored
    * @return        - a new trie with the new message included
    */
  final def add(message: List[Word], replies: (Option[String], Set[String]), trie: Trie): Trie = {
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

    go(trie, message)
  }

  /**
    * The algorithm describes the search of a message in a trie, by parsing every word and matching it,
    * thus returning a Set of possible replies depending on bot's previous replies.
    *
    * @param message - the sentence that is to be found, or not
    * @param trie  - the trie in which it will be searched
    * @return      - returns a Set of (previousMessageFromBot, Set[possible replies]),
    *              from which another algorithm will pick the best choice.
    */
  @tailrec
  final def search(message: List[Word], trie: Trie): Set[(Option[String], Set[String])] = {
    if (message.isEmpty)
      trie.replies //completely ran over all the words
    else {
      val next = trie.children.find(t => isMatching(t, message.head))
      next match {
        case None            => Set() //word wasn't found in the trie
        case Some(otherTrie) => search(message.tail, otherTrie) //going deeper
      }
    }
  }

  /**
    * @return - all the possible messages where the last bot message is equal to the one given.
    */
  final def search(lastBotMessage: String, trie: Trie): Set[(Option[String], Set[String])] = {
      def getAllReplies(curr: Trie): Set[(Option[String], Set[String])] =
        curr.replies ++ curr.children.flatMap(c => getAllReplies(c))

    getAllReplies(trie).filter(n => n._1 match {
      case None      => false
      case Some(msg) => msg == lastBotMessage
    })
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
    * @param t - trie word
    * @param w - message word
    * @return whether the message matches the trie node, both word-wise and attribute-wise
    */
  private def isMatching(t: Trie, w: Word): Boolean =
    t.curr._1.pattern.matcher(w._1.regex).matches() && t.curr._2 == w._2

  private def createNode(word: Word): Trie = Trie(word, Set[Trie]().empty, Set((None, Set[String]().empty)))

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
