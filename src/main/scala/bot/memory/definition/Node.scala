package bot.memory.definition

import bot.memory.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


sealed trait NodeInformation {
  def exists(p: PartOfSentence): Boolean
}

case class NodeSimpleWord(word: Regex,
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
}

case class NodeUserInformation(word: Regex = "".r,
                               attribute: Option[Attribute] = None)
  extends NodeInformation {
  override def exists(p: PartOfSentence): Boolean = {
    p.attribute match {
      case None => false
      case Some(attr) => p.word.toString().matches(this.word) && attribute.contains(attr)
    }
  }
}


