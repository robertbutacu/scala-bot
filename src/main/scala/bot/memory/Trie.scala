package bot.memory

import bot.learn.PossibleReply
import bot.memory.definition.{NodeInformation, NodeSimpleWord, PartOfSentence}

/**
  *
  * @param information - current node, containing a word( or a regex), and an optional attribute
  *                    which is to be stored in map if necessary
  *                    Regex             - current word , part of the message
  *                    Option[Attribute] - in case it is something to be remembered
  * @param children    - next parts of a possible message from a client.
  * @param replies     - replies to the message starting from the top of the trie all the way down to curr.
  *                    Option[() => Set[String]    - a function returning a possible last bot message
  *                    Set[f: Any => Set[String] ] - a set of functions returning a set of possible replies
  *                    => It is done that way so that the replies are generated dynamically,
  *                    depending on the already existing/non-existing attributes.
  **/
case class Trie(information: NodeInformation,
                children:    Set[Trie]          = Set.empty,
                replies:     Set[PossibleReply] = Set.empty)

object Trie {
  def apply(node: PartOfSentence): Trie = {
    Trie(NodeInformation(node))
  }

  def empty: Trie = Trie(NodeSimpleWord("".r))
}

