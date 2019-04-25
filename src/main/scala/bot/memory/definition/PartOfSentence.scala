package bot.memory.definition

import bot.connections.Attribute

import scala.util.matching.Regex

case class PartOfSentence(word:      Regex             = "".r,
                          attribute: Option[Attribute] = None) {
  def matchesWord(toMatch: Word): Boolean =
    this.word.pattern.pattern().matches(toMatch.word)  || toMatch.otherAcceptableForms.exists(af => this.word.pattern.pattern().matches(af.word))
}