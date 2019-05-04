package bot.memory

import bot.memory.definition.{Definition, Synonym, Word}

object Utils {
  def findReplacements(word:       String,
                       sentence:   List[String],
                       dictionary: Set[Definition]): Set[Word] = {
    // For the input word, find out how many synonyms there are by:
    // checking for each synonym how many context words there are
    // a synonym can go either way: definition => synonym, as well as synonym => definition
    val cleanedSentence = sentence.filterNot(_ == word)

    def contextCountForSynonym(synonym: Synonym): Int             = cleanedSentence.count(s => synonym.contextWords.exists(w => w.matches(s)))
    def findMatchingDefinition:                   Set[Definition] = dictionary.filter(d => d.matches(word))
    def wordAsSynonym:                            Set[Word]       = dictionary.withFilter(d => d.isSynonym(word, sentence)).map(_.word)

    //TODO this is pretty dumb, as any synonym with at least 1 context word gets picked up
    val allSynonyms = findMatchingDefinition.flatMap(d => d.getMatchingSynonyms(word, sentence))
      .withFilter(s => contextCountForSynonym(s) > 0)
      .map(_.definition)

    allSynonyms ++ wordAsSynonym
  }
}
