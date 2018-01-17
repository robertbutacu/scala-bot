package bot.memory.definition

import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex

case class Word(word: String,
                otherAcceptableForms: Set[Word] = Set.empty,
                partOfSpeech: PartOfSpeech = Irrelevant)

case class Definition(word: Word, synonyms: Set[Synonym] = Set.empty)

object Definition {
  def ==(that: Definition, other: Definition): Boolean = that.word == other.word

  def merge(definition: Definition): Set[Word] = definition.synonyms.map(_.definition) + definition.word

  def find(word: Regex, definitions: List[Definition]): Boolean = definitions exists (_.word == word)

  def addDefinitions(word: Definition, definitions: Set[Synonym]): Definition =
    Definition(word.word, word.synonyms ++ definitions)
}