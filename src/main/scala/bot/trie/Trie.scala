package bot.trie

import bot.handler.Characteristic

import scala.util.matching.Regex

/**
  *
  * @param curr     - current node, containing a word( or a regex), and an optional attribute
  *                 which is to be stored in map if necessary
  *                 Regex             - current word , part of the message
  *                 Option[Attribute] - in case it is something to be remembered
  * @param children - next parts of a possible message from a client.
  * @param replies  - replies to the message starting from the top of the trie all the way down to curr.
  *                 Option[() => Set[String]    - a function returning a possible last bot message
  *                 Set[f: Any => Set[String] ] - a set of functions returning a set of possible replies
  *                 => It is done that way so that the replies are generated dynamically,
  *                 depending on the already existing/non-existing attributes.
  **/
case class Trie(curr: (Regex, Option[Attribute]) = ("".r, None),
                children: Set[Trie] = Set[Trie]().empty,
                replies: Set[
                  (Option[() => Set[String]],
                    Set[() => Set[String]])] = Set((None, Set(() => Set[String]().empty)))) {
  def addValue(node: Trie): Trie =
    Trie(curr, children ++ Set(node), replies)
}

case class Attribute(characteristic: Characteristic, weigh: Int)
