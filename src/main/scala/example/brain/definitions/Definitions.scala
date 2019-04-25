package example.brain.definitions

import bot.memory.definition.{Definition, Synonym, Word}

import scala.language.implicitConversions
import scala.util.matching.Regex

object Definitions {

  def get(): Set[Definition] = {

    implicit def convertString(s: String):               Regex   = s.r
    implicit def convertDefinitionToWord(d: Definition): Word    = d.word
    implicit def convertDefinition(d: Definition):       Synonym = Synonym(d)
    implicit def convertToWord(d: String):               Word    = Word(d)

    val underage: Definition = Definition(Word("underage"))
    val minor:    Definition = Definition(Word("minor"))

    val old:    Definition = Definition(Word("age"))
    val ageOld: Definition = Definition(Word("age-old"), Set(old))

    val passionate: Definition = Definition(Word("passionate"))
    val ardent:     Definition = Definition(Word("ardent"))
    val keen:       Definition = Definition(Word("keen"))

    val greetings: Definition = Definition(Word("greetings"))
    val hi:        Definition = Definition(Word("hi"))
    val hello:     Definition = Definition(Word("hello"))
    val whatsup:   Definition = Definition(Word("hello"))

    Set(Definition.addSynonyms(underage, Set(Synonym(minor, Set(old, ageOld)))),
      Definition.addSynonyms(minor, Set(underage)),
      Definition.addSynonyms(old, Set(ageOld)),
      Definition.addSynonyms(ageOld, Set(old)),
      Definition.addSynonyms(passionate, Set(ardent, keen)),
      Definition.addSynonyms(ardent, Set(passionate, keen)),
      Definition.addSynonyms(keen, Set(passionate, ardent)),
      Definition.addSynonyms(greetings, Set(hi, hello, whatsup)),
      Definition.addSynonyms(hi, Set(greetings, hello, whatsup)),
      Definition.addSynonyms(hello, Set(greetings, hi, whatsup)),
      Definition.addSynonyms(whatsup, Set(greetings, hi, hello))
    )
  }
}
