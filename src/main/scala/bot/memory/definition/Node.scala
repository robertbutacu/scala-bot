package bot.memory.definition

import bot.memory.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex

protected[memory] case class Node(word: NodeWord,
                                  otherAcceptableForms: Set[Node] = Set.empty,
                                  partOfSpeech: PartOfSpeech = Irrelevant,
                                  acceptableSynonyms: Set[Node] = Set.empty
                                 )

protected[memory] case class NodeWord(word: Regex = "".r,
                                      attribute: Option[Attribute] = None) {

  def matchesWord(toMatch: Word): Boolean =
    toMatch
      .otherAcceptableForms
      .exists(this.matchesWord) || toMatch.word == this.word.toString()
}

