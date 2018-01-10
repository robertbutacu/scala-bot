package bot.trie.definition

import scala.util.matching.Regex

case class Definition(word: Word, synonyms: Set[Synonym] = Set.empty)

object Definition {
  def ==(that: Definition, other: Definition): Boolean = that.word == other.word

  def merge(definition: Definition): Set[Definition] = definition.synonyms.map(_.definition) + definition

  def find(word: Regex, definitions: List[Definition]): Boolean = definitions exists (_.word == word)

  def addDefinitions(word: Definition, definitions: Set[Synonym]): Definition =
    Definition(word.word, word.synonyms ++ definitions)
}