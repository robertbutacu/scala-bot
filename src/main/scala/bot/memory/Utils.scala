package bot.memory

import bot.memory.definition.{Definition, PartOfSentence, Synonym, Word}

protected[memory] object Utils {
  def findReplacements(sentence: List[PartOfSentence],
                       word: PartOfSentence,
                       dictionary: Set[Definition]): List[Word] = {

    case class ContextMatchCount(synonym: Synonym, count: Int) {
      def unapply(): Word = this.synonym.definition
    }

    //Option so it can be used as a Monad in the for comprehension
    def wordsInSameContextCount(sentence: List[PartOfSentence],
                                contextWords: Set[Word]): Option[Int] = {
      val count = sentence count (w => contextWords exists w.matchesWord)

      if (count > 0) Some(count)
      else None
    }

    val definitionM = dictionary find { p => p.equals(word) }

    //having the definition found out, try to zip each synonym with the number of words found in that context
    val contextsCount = for {
      definition <- definitionM.toList
      synonym <- definition.synonyms
      count <- wordsInSameContextCount(sentence, synonym.contextWords)
      if count > 0
    } yield ContextMatchCount(synonym, count)

    //max by apparitions
    val maxMatch = contextsCount.maxBy(_.count).count

    contextsCount filter (_.count == maxMatch) map (_.unapply())
  }
}
