package bot.memory.definition.part.of.speech

sealed trait PartOfSpeech

case class Noun(grammaticalNumber: GrammaticalNumber = Singular) extends PartOfSpeech
case class Adjective(grammaticalNumber: GrammaticalNumber = Singular) extends PartOfSpeech
case class Pronoun(grammaticalNumber: GrammaticalNumber = Singular) extends PartOfSpeech
case object Adverb extends PartOfSpeech
case class Verb(tense: VerbTense = Present) extends PartOfSpeech
case object Preposition extends PartOfSpeech
case object Conjunction extends PartOfSpeech
case object Interjection extends PartOfSpeech
case object Irrelevant extends PartOfSpeech

