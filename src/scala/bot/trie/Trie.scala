package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

case class Leaf(replies: Set[String])

case class Node(curr: (Regex, Option[Attribute]), next: Set[Node], leafs: Leaf) {
  def addValue(node: Node): Node =
    Node(curr, next ++ Set(node), leafs)
}
