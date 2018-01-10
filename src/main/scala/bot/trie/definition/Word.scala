package bot.trie.definition

import scala.util.matching.Regex

case class Word(word: Regex,
                otherAcceptableForms: Set[Word] = Set.empty,
                grammaticalNumber: Option[GrammaticalNumber] = None
               )
