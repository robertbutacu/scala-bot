package bot.memory.definition

import bot.connections.Attribute
import bot.memory.Utils
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


sealed trait NodeInformation {
  def informationMatches(p: PartOfSentence): Boolean

  def wordMatches(w: String): Boolean

  def addToAttributes(value:       String,
                      information: Map[Attribute, String]): Map[Attribute, String]
}

object NodeInformation {
  def apply(p: PartOfSentence, sentence: List[PartOfSentence], dictionary: Set[Definition]): NodeInformation = {
    def constructSimpleNode(): NodeSimpleWord = {
      val word = p.word
      val otherAcceptableForms = dictionary.find(_.word.matches(p.word.toString())).fold(Set.empty[AcceptableForm])(w => w.word.otherAcceptableForms)
      // TODO inconsistency between List[PartOfSentence] and findReplacements
      val synonyms = Utils.findReplacements(p.word.toString(), sentence.map(_.word.toString()), dictionary)

      NodeSimpleWord(word, otherAcceptableForms, Irrelevant, synonyms)
    }

    p.attribute match {
      case None    => constructSimpleNode()
      case Some(a) => NodeUserInformation(p.word, a)
    }
  }
}


//TODO maybe word and otherAcceptableForms should be regexes??? More flexibility
case class NodeSimpleWord(word:                 Regex               = "".r,
                          otherAcceptableForms: Set[AcceptableForm] = Set.empty,
                          partOfSpeech:         PartOfSpeech        = Irrelevant,
                          synonyms:             Set[Word]           = Set.empty
                         )
  extends NodeInformation {
  override def informationMatches(p: PartOfSentence): Boolean = {
    def anyMatch(word: String): Boolean =
      this.word.toString() == word || otherAcceptableForms.contains(AcceptableForm(word)) || synonyms.exists(w => w.matches(word))

    p.attribute match {
      case None    => anyMatch(p.word.toString())
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


