package bot.memory

import scala.util.matching.Regex

protected trait TrieOperations {
  type Word = (Regex, Option[Attribute])

  /**
    * @param t - trie word
    * @param w - message word
    * @return whether the message matches the trie node
    */
  def isMatching(t: SpeakingKnowledge, w: Word): Boolean =
    t.curr._1.pattern.matcher(w._1.regex).matches()

  def isMatching(node: Word, that: Word): Boolean =
    node._1.regex == that._1.regex && node._2 == that._2
}
