package bot.memory.definition

import bot.memory.definition.part.of.speech.{Irrelevant, PartOfSpeech}

import scala.util.matching.Regex

case class Word(word: Regex,
                otherAcceptableForms: Set[Word] = Set.empty,
                partOfSpeech: PartOfSpeech = Irrelevant
               )
