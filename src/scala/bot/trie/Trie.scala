package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

/**
  *
  * @param curr - current node, containing a word( or a regex), and an optional attribute
  *             which is to be stored in map if necessary
  * @param children - next parts of a possible message from a client.
  * @param replies  - replies to the message starting from the top of the trie all the way down to curr.
  */
case class Trie(curr: (Regex, Option[Attribute]), children: Set[Trie],
                replies: Set[(Option[String], Set[String])]) {
  def addValue(node: Trie): Trie =
    Trie(curr, children ++ Set(node), replies)
}
