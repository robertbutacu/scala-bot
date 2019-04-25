package example.brain.definitions

import bot.memory.definition.{Definition, Synonym, Word}

import scala.language.implicitConversions
import scala.util.matching.Regex

object Definitions {

  def get(): Set[Definition] = {

    implicit def convertString(s: String):                Regex = s.r
    implicit def convertDefinitionToWord(d: Definition):  Word    = d.word
    implicit def convertDefinition(d: Definition):        Synonym = Synonym(d)
    implicit def convertToWord(d: String):                Word    = Word(d)

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

    Set(Definition.addDefinitions(underage, Set(minor)),
      Definition.addDefinitions(minor, Set(underage)),
      Definition.addDefinitions(old, Set(ageOld)),
      Definition.addDefinitions(ageOld, Set(old)),
      Definition.addDefinitions(passionate, Set(ardent, keen)),
      Definition.addDefinitions(ardent, Set(passionate, keen)),
      Definition.addDefinitions(keen, Set(passionate, ardent)),
      Definition.addDefinitions(greetings, Set(hi, hello, whatsup)),
      Definition.addDefinitions(hi, Set(greetings, hello, whatsup)),
      Definition.addDefinitions(hello, Set(greetings, hi, whatsup)),
      Definition.addDefinitions(whatsup, Set(greetings, hi, hello))
    )
  }
}
