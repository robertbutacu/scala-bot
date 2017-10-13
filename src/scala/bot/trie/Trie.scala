package scala.bot.trie

import scala.bot.handler.Attribute
import scala.util.matching.Regex

case class Leaf(possibleReplies: List[String])
case class Node(current: (Regex, Option[Attribute]), children: Set[Node], leafs: Set[Leaf])
