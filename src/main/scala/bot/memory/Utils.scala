package bot.memory

import bot.memory.definition.{Definition, NodeWord, Synonym, Word}

protected[memory] object Utils {

  def findReplacements(sentence: List[NodeWord], word: NodeWord, dictionary: Set[Definition]): List[Word] = {
    case class ContextMatchCount(synonym: Synonym, count: Int) {
      def unapply(): Word = this.synonym.definition
    }

    def wordsInSameContextCount(sentence: List[NodeWord], contextWords: Set[Word]): Option[Int] = {
      val count = sentence count ( w => contextWords exists w.matchesWord )

      if (count > 0) Some(count)
      else           None
    }

    val definitionM = dictionary find { p => p.word.word == word.word.toString() }

    //having the definition found out, try to zip each synonym with the number of words found in that context
    val contextsCount = for {
      definition <- definitionM.toList
      synonym <- definition.synonyms
      count <- wordsInSameContextCount(sentence, synonym.contextWords)
      if count > 0
    } yield ContextMatchCount(synonym, count)

    //max by apparitions
    val maxMatch = contextsCount.maxBy(_.count)

    contextsCount filter (_.count == maxMatch) map (_.unapply())
  }
}
