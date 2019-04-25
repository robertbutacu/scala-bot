package bot.memory.definition

import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

case class Definition(word:     Word,
                      synonyms: Set[Synonym] = Set.empty) {
  // Inverse relationship where the word is found as a synonym for the current definition,
  // and thus the current definition for the word is valid
  def isSynonym(word: String, sentence: List[String]): Boolean = {
    val matchingSynonyms = synonyms.filter(s => s.definition.word == word)

    matchingSynonyms.exists {
      s =>
        s.contextWords.count(w => sentence.exists(s => w.matches(s))) > 0
    }
  }

  // Direct relationship where the synonyms from a definition are extracted using the current word and the context
  def getMatchingSynonyms(word: String, sentence: List[String]): Set[Synonym] = {
    synonyms.filter { s =>
      sentence.contains(s.definition.word) || sentence.exists(w => s.contextWords.exists(ww => ww.matches(w)))
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


  def addSynonyms(word:     Definition,
                  synonyms: Set[Synonym]): Definition =
    word.copy(synonyms = word.synonyms ++ synonyms)
}

case class AcceptableForm(word: String)