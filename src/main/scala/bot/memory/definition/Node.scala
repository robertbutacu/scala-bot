package bot.memory.definition

import bot.memory.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


trait NodeInformation {
  def find: Option[Regex]
}

case class NodeSimpleWord(word: Regex,
                                            otherAcceptableForms: Set[NodeInformation] = Set.empty,
                                            partOfSpeech: PartOfSpeech = Irrelevant,
                                            synonyms: Set[NodeInformation] = Set.empty
                                           )
  extends NodeInformation {
  override def find: Option[Regex] = None
}

case class NodeUserInformation(word: Regex = "".r,
                                                 attribute: Option[Attribute] = None)
  extends NodeInformation {
  override def find: Option[Regex] = None
}


