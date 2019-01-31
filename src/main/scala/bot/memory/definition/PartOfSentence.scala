package bot.memory.definition

import bot.connections.Attribute

import scala.util.matching.Regex

case class PartOfSentence(word: Regex = "".r,
                          attribute: Option[Attribute] = None) {
  def matchesWord(toMatch: Word): Boolean =
    toMatch
      .otherAcceptableForms
      .exists(this.matchesWord) || toMatch.word == this.word.toString()
}