package bot.memory.definition

import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex

case class Definition(word:     Word,
                      synonyms: Set[Synonym] = Set.empty) {
  def equals(other: String): Boolean = {
    //TODO needs an algorithm to infer the context and give out the proper definition
    word.word == other || synonyms.exists(s => other == s.definition.word)
  }
}

case class Synonym(definition:   Word,
                   contextWords: Set[Word] = Set.empty)

case class Word(word:                 String,
                otherAcceptableForms: Set[Word] = Set.empty,
                partOfSpeech:         PartOfSpeech = Irrelevant)

object Definition {
  def getWords(definition: Definition): Set[Word] =
    definition.synonyms.map(_.definition) + definition.word

  def find(word:        String,
           definitions: List[Definition]): Boolean =
    definitions exists (_.word.word == word)

  def addDefinitions(word:        Definition,
                     definitions: Set[Synonym]): Definition =
    word.copy(synonyms = word.synonyms ++ definitions)
}