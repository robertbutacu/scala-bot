package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

case class Leaf(possibleReplies: Set[String])
case class Node(current: (Regex, Option[Attribute]), children: Set[Node], leafs: Leaf){
  def addValue(node: Node): Node =
    Node(current, children ++ Set(node), leafs)
}
