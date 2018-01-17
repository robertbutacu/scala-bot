package bot.memory.definition

import bot.memory.Attribute
import bot.memory.definition.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex

case class Word(word: Meaning,
                otherAcceptableForms: Set[Word] = Set.empty,
                partOfSpeech: PartOfSpeech = Irrelevant
               )

case class Meaning(word: Regex = "".r, attribute: Option[Attribute] = None)

