package bot.memory.definition

import bot.connections.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


sealed trait NodeInformation {
  def informationMatches(p: PartOfSentence): Boolean

  def wordMatches(w: String): Boolean

  def addToAttributes(value:       String,
                      information: Map[Attribute, String]): Map[Attribute, String]
}

object NodeInformation {
  def apply(p: PartOfSentence, dictionary: Set[Definition]): NodeInformation = {
    p.attribute match {
      case None    => NodeSimpleWord(p.word)
      case Some(a) => NodeUserInformation(p.word, a)
    }
  }
}

case class NodeSimpleWord(word:                 Regex        = "".r,
                          otherAcceptableForms: Set[Regex]   = Set.empty,
                          partOfSpeech:         PartOfSpeech = Irrelevant,
                          synonyms:             Set[Regex]   = Set.empty
                         )
  extends NodeInformation {
  override def informationMatches(p: PartOfSentence): Boolean = {
    def anyMatch(): Boolean =
      this.word.toString() == p.word.toString() || otherAcceptableForms.contains(p.word) || synonyms.contains(p.word)

    p.attribute match {
      case None    => anyMatch()
      case Some(_) => false
    }
  }

  override def addToAttributes(p:           String,
                               information: Map[Attribute, String]): Map[Attribute, String] =
    information

  override def wordMatches(w: String): Boolean = word.pattern.matcher(w).matches()
}

case class NodeUserInformation(word:      Regex = "".r,
                               attribute: Attribute)
  extends NodeInformation {
  override def informationMatches(p: PartOfSentence): Boolean = {
    p.attribute match {
      case None       => false
      case Some(attr) => this.word.toString() == p.word.toString() && attribute == attr
    }
  }

  override def addToAttributes(p:           String,
                               information: Map[Attribute, String]): Map[Attribute, String] =
    information + (this.attribute -> p)

  override def wordMatches(w: String): Boolean = word.pattern.matcher(w).matches()
}


