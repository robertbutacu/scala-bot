package bot.trie

import scala.util.matching.Regex

protected trait TrieOperations {
  type Word = (Regex, Option[Attribute])
  /**
    * Map[Attribute, String] => represents all the attributes that were found along the way which
    * composed the message sent by the user, and that will be later stored by
    * the MessageHandler if its all good.
    * Set[(Option[() => Set[String]], Set[() => Set[String]])] =>
    * a set of tuples:
    * _1 => an optional parameter of a function that returns a Set of Strings representing
    * the last message the bot sent
    * _2 => a set of functions that return a set of strings for a particular set of
    * messages the bot sent
    */
  type SearchResponse = (Map[Attribute, String], Set[(Option[() => Set[String]], Set[() => Set[String]])])


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
