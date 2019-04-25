package bot.memory.definition

import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

case class Definition(word:     Word,
                      synonyms: Set[Synonym] = Set.empty) {
  def getMatchingSynonyms(word: String, sentence: List[String]): Set[Synonym] = {
    synonyms.filter { s =>
      sentence.contains(s.definition.word) && sentence.exists(w => s.contextWords.exists(ww => ww.matches(w)))
    }
  }

  def matches(w: String): Boolean = word.matches(w)
}

case class Synonym(definition:   Word,
                   contextWords: Set[Word] = Set.empty)

case class Word(word:                 String,
                otherAcceptableForms: Set[AcceptableForm] = Set.empty,
                partOfSpeech:         PartOfSpeech = Irrelevant) {
  // TODO this should be @tailrec
  def matches(other: String): Boolean = {
    word == other || otherAcceptableForms.exists(w => w.word == other)
  }

  def getWords: Set[AcceptableForm] = otherAcceptableForms + AcceptableForm(this.word)
}

object Definition {
  def getWords(definition: Definition): Set[Word] = definition.synonyms.map(_.definition) + definition.word


  def find(word:        String,
           definitions: List[Definition]): Boolean =
    definitions exists (_.word.word == word)


  def addDefinitions(word:        Definition,
                     definitions: Set[Synonym]): Definition =
    word.copy(synonyms = word.synonyms ++ definitions)
}

case class AcceptableForm(word: String)