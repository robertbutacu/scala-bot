package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex


case class Node(curr: (Regex, Option[Attribute]), children: Set[Node], replies: Set[String]) {
  def addValue(node: Node): Node =
    Node(curr, children ++ Set(node), replies)
}
