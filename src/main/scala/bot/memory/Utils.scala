package bot.memory

import bot.memory.definition.{Definition, NodeWord, Synonym, Word}

protected[memory] object Utils {

  def findReplacements(sentence: List[NodeWord], word: NodeWord, dictionary: Set[Definition]): List[Word] = {
    case class ContextMatchCount(synonym: Synonym, count: Int)

    def wordsInSameContext(sentence: List[NodeWord], contextWords: Set[Word]): Int = {
      for {
        word <- sentence
        contextWord <- contextWords
        otherAcceptableForms <- contextWord.otherAcceptableForms
      } yield word.word.toString() == contextWord.word || word.toString == otherAcceptableForms.word
    }.size

    val definitionM = dictionary find { p => p.word.word == word.word.toString() }

    //having the definition found out, try to zip each synonym with the number of words found in that context

    val contextCount = definitionM map { d =>
      d.synonyms
        .filter(s => wordsInSameContext(sentence, s.contextWords) > 0)
        .map(s => ContextMatchCount(s, wordsInSameContext(sentence, s.contextWords)))
    }
    /*for {
      definition <- definitionM
      synonym <- definition.synonyms
      if wordsInSameContext(sentence, synonym.contextWords) > 0
    } yield ContextMatchCount(synonym, wordsInSameContext(sentence, synonym.contextWords))
    */
    //max by apparitions
    val maxMatch = contextCount.map(c => c.maxBy(_.count).count)

    contextCount.map(c => c.filter(cc => maxMatch.getOrElse(-1) == cc.count))
      .map(cc => cc.map(_.synonym.definition))
      .getOrElse(List.empty).toList
  }
}
