package bot.memory

import bot.memory.definition.{Definition, NodeWord, Synonym, Word}

protected[memory] object Utils {

  def findReplacements(sentence: List[NodeWord], word: NodeWord, dictionary: Set[Definition]): List[Word] = {
    case class ContextMatchCount(synonym: Synonym, count: Int) {
      def unapply(): Word = this.synonym.definition
    }

    def wordsInSameContext(sentence: List[NodeWord], contextWords: Set[Word]): Int = {
      for {
        word <- sentence
        contextWord <- contextWords
        otherAcceptableForms <- contextWord.otherAcceptableForms
      } yield word.word.toString() == contextWord.word || word.toString == otherAcceptableForms.word
    }.size

    val definitionM = dictionary find { p => p.word.word == word.word.toString() }

    //having the definition found out, try to zip each synonym with the number of words found in that context
    val contextsCount =     for {
      definition <- definitionM.toList
      synonym <- definition.synonyms
      if wordsInSameContext(sentence, synonym.contextWords) > 0
    } yield ContextMatchCount(synonym, wordsInSameContext(sentence, synonym.contextWords))

    //max by apparitions
    val maxMatch = contextsCount.maxBy(_.count)

    contextsCount filter { _.count == maxMatch} map { _.unapply()}
  }
}
