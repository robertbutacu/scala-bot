package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex


case class Trie(curr: (Regex, Option[Attribute]), children: Set[Trie],
                replies: Set[(Option[String], Set[String])]) {
  def addValue(node: Trie): Trie =
    Trie(curr, children ++ Set(node), replies)
}
