package bot.memory.definition

import bot.memory.Attribute
import bot.memory.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex


protected[memory] trait NodeInformation

protected[memory] case class NodeSimpleWord(word: String,
                                            otherAcceptableForms: Set[NodeInformation] = Set.empty,
                                            partOfSpeech: PartOfSpeech = Irrelevant,
                                            synonyms: Set[NodeInformation] = Set.empty
                                           )
  extends NodeInformation

protected[memory] case class NodeUserInformation(word: Regex = "".r,
                                                 attribute: Option[Attribute] = None)
  extends NodeInformation


