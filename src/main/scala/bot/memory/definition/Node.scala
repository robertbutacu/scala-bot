package bot.memory.definition

import bot.memory.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


sealed trait NodeInformation {
  def exists(p: PartOfSentence): Boolean

  def addInformation(p: PartOfSentence,
                     information: Map[Attribute, String]): Map[Attribute, String]
}

case class NodeSimpleWord(word: Regex = "".r,
                          otherAcceptableForms: Set[Regex] = Set.empty,
                          partOfSpeech: PartOfSpeech = Irrelevant,
                          synonyms: Set[Regex] = Set.empty
                         )
  extends NodeInformation {
  override def exists(p: PartOfSentence): Boolean = {
    def anyMatch(): Boolean =
      this.word == p.word || otherAcceptableForms.contains(p.word) || synonyms.contains(p.word)

    p.attribute match {
      case None => anyMatch()
      case Some(_) => false
    }
  }

  override def addInformation(p: PartOfSentence,
                              information: Map[Attribute, String]): Map[Attribute, String] =
    information
}

case class NodeUserInformation(word: Regex = "".r,
                               attribute: Attribute)
  extends NodeInformation {
  override def exists(p: PartOfSentence): Boolean = {
    p.attribute match {
      case None => false
      case Some(attr) =>
        this.word.pattern.matcher(p.word.toString()).matches() && attribute == attr
    }
  }

  override def addInformation(p: PartOfSentence,
                              information: Map[Attribute, String]): Map[Attribute, String] =
    information + (this.attribute -> p.word.toString())
}


