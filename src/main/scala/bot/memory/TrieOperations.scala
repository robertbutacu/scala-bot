package bot.memory

import bot.learn.{PossibleReply, SearchResponses}
import bot.memory.definition.{NodeInformation, PartOfSentence}

import scala.util.matching.Regex

protected trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  def information: NodeInformation
  def children: Set[Trie]
  def replies: Set[PossibleReply]

  /**
    * @param t - trie word
    * @param w - message word
    * @return whether the message matches the trie node
    */
  def isMatching(t: Trie, w: PartOfSentence): Boolean =
    false

  def isMatching(node: PartOfSentence, that: PartOfSentence): Boolean =
    false

  def add(message: List[PartOfSentence], replies: PossibleReply): Trie

  def search(message: List[PartOfSentence]): SearchResponses
}
