package bot.memory.part.of.speech

sealed trait VerbTense

case object Past extends VerbTense
case object Present extends VerbTense
case object Future extends VerbTense