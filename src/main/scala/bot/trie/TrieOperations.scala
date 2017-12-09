package bot.trie

import scala.annotation.tailrec
import scala.util.matching.Regex

object TrieOperations {
  type Word = (Regex, Option[Attribute])
  /**
    * Map[Attribute, String] => represents all the attributes that were found along the way which
    * composed the message sent by the user, and that will be later stored by
    * the MessageHandler if its all good.
    * Set[(Option[() => Set[String]], Set[() => Set[String]])] =>
    * a set of tuples:
    * _1 => an optional parameter of a function that returns a Set of Strings representing
    * the last message the bot sent
    * _2 => a set of functions that return a set of strings for a particular set of
    * messages the bot sent
    */
  type SearchResponse = (Map[Attribute, String], Set[(Option[() => Set[String]], Set[() => Set[String]])])

  /**
    * For a current Trie, the algorithm returns another trie with the message added.
    * The pattern matching does the following:
    *   1. in case of None => current word isn't in the set => add it, and call the function with the same node
    *   2. in case of Some(next) => next node has been found => remove it from the Set, since its gonna be different 
    *    it would double stack otherwise
    * @param message - list of words to be added into the trie
    * @param replies - a set of functions which return a set of possible replies
    * @param trie    - trie where the message will be stored
    * @return - a new trie with the new message included
    */
  final def add(message: List[Word],
                replies: (Option[() => Set[String]],
                  Set[() => Set[String]]),
                trie: SpeakingKnowledge): SpeakingKnowledge = {

    def go(curr: SpeakingKnowledge, words: List[Word]): SpeakingKnowledge = {
      if (words.isEmpty) //went through all the list
        addReplies(curr, replies) // adding the replies to the Set
      else {
        curr.children.find(n => isMatching(n.curr, words.head)) match {
          case None       => go(curr.addValue(SpeakingKnowledge(words.head)), words)
          case Some(next) => SpeakingKnowledge(curr.curr,
            curr.children - next ++ Set(go(next, words.tail)),
            curr.replies)
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
    * @param trie    - the trie in which it will be searched
    * @return - returns a Set of (previousMessageFromBot, Set[functions returning possible replies]),
    *         from which another algorithm will pick the best choice.
    */
  final def search(message: List[Word], trie: SpeakingKnowledge): SearchResponse = {

    @tailrec
    def go(message: List[Word], trie: SpeakingKnowledge,
           attributes: Map[Attribute, String]): SearchResponse = {
      if (message.isEmpty)
        (attributes, trie.replies) //completely ran over all the words
      else {
        val head = message.head
        val next = trie.children.find(t => isMatching(t, head))
        next match {
          case None           => (attributes, Set()) //word wasn't found in the trie
          case Some(nextNode) => go(message.tail, nextNode,
            nextNode.curr._2 match {
              case None       => attributes
              case Some(attr) => attributes + (attr -> head._1.regex)
            })
        }
      }
    }

    go(message, trie, Map[Attribute, String]().empty)
  }

  def printTrie(trie: SpeakingKnowledge): Unit = {
    println("Node " + trie.curr + "   ")
    trie.replies.foreach(r => println("Leaf " + r))
    trie.children.foreach(t => printTrie(t))
  }

  /**
    *
    * @param t       - trie on which is operated
    * @param replies - replies that are to be added
    * @return     - new leafs which also contain the new replies
    *             There are 2 cases:
    *             1. when they depend on a previous bot message ( or lack of) also stored:
    *             the replies are appended to the already existing replies.
    *             2. when they aren't stored at all:
    *             they are registered as new replies with their attribute.
    */
  private def addReplies(t: SpeakingKnowledge, replies: (Option[() => Set[String]], Set[() => Set[String]])): SpeakingKnowledge =
    t.replies.find(l => l._1 == replies._1) match {
      case None      => SpeakingKnowledge(t.curr, t.children, t.replies ++ Set(replies))
      case Some(rep) => SpeakingKnowledge(t.curr, t.children, t.replies -- Set(rep) ++ Set((rep._1, rep._2 ++ replies._2)))
    }

  /**
    * @param t - trie word
    * @param w - message word
    * @return whether the message matches the trie node
    */
  private def isMatching(t: SpeakingKnowledge, w: Word): Boolean =
    t.curr._1.pattern.matcher(w._1.regex).matches()

  private def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
