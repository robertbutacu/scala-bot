package bot.memory

import bot.memory.definition.{Definition, NodeWord, Synonym, Word}

import scala.util.matching.Regex

protected[memory] object Utils {

  def findReplacements(sentence: List[NodeWord], word: NodeWord, dictionary: Set[Definition]): List[Word] = {
    case class ContextMatchCount(synonym: Synonym, count: Int) {
      def unapply(): Word = this.synonym.definition
    }

    def wordsInSameContext(sentence: List[NodeWord], contextWords: Set[Word]): Option[Int] = {
      implicit def nodeWordToString(n: NodeWord): String = n.word.toString()

      def isMatch(nw: String, toMatch: Word): Boolean = {
        toMatch.word == nw || toMatch.otherAcceptableForms.exists(isMatch(nw, _))
      }

      val count = sentence count {
        w => contextWords exists (isMatch(w, _))
      }

      if (count > 0) Some(count)
      else None
    }

    val definitionM = dictionary find { p => p.word.word == word.word.toString() }

    //having the definition found out, try to zip each synonym with the number of words found in that context
    val contextsCount = for {
      definition <- definitionM.toList
      synonym <- definition.synonyms
      count <- wordsInSameContext(sentence, synonym.contextWords).toList
      if count > 0
    } yield ContextMatchCount(synonym, count)

    //max by apparitions
    val maxMatch = contextsCount.maxBy(_.count)

    contextsCount filter (_.count == maxMatch) map (_.unapply())
  }
}
