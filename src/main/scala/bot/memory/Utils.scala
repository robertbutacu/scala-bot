package bot.memory

import bot.memory.definition.{Definition, NodeWord, Synonym, Word}

protected[memory] object Utils {

  def findReplacements(sentence: List[NodeWord], word: NodeWord, dictionary: Set[Definition]): List[Word] = {
    def wordsInSameContext(sentence: List[NodeWord], contextWords: Set[Word]): Int = {
      for {
        word <- sentence
        contextWord <- contextWords
        otherAcceptableForms <- contextWord.otherAcceptableForms
      } yield word.word.toString() == contextWord.word || word.toString == otherAcceptableForms.word
    }.size

    val definitionM = dictionary find { p => p.word.word == word.word.toString() }

    //having the definition found out, try to zip each synonym with the number of words found in that context

    val contextCount = for {
      definition <- definitionM
      synonym <- definition.synonyms
      if wordsInSameContext(sentence, synonym.contextWords) > 0
    } yield (synonym, wordsInSameContext(sentence, synonym.contextWords))

    //max by apparitions
    val maxMatch = contextCount.maxBy(_._2) ._2

    contextCount.filter { _._2 == maxMatch }
      .map {w: (Synonym, Int) => w._1.definition}
      .toList
  }
}
