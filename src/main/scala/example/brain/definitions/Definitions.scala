package example.brain.definitions

import bot.memory.definition.{Definition, Synonym, Word}

import scala.util.matching.Regex

object Definitions {

  def get(): Set[Definition] = {

    implicit def convertString(s: String): Regex = s.r

    implicit def convertDefinition(d: Definition): Synonym = Synonym(d)
    implicit def convertToWord(d: String): Word = Word(d)

    val underage: Definition = Definition("underage")
    val minor: Definition = Definition("minor")

    val old: Definition = Definition("age")
    val ageOld: Definition = Definition("age-old", Set(old))

    val passionate: Definition = Definition("passionate")
    val ardent: Definition = Definition("ardent")
    val keen: Definition = Definition("keen")

    val greetings: Definition = Definition("greetings")
    val hi: Definition = Definition("hi")
    val hello: Definition = Definition("hello")
    val whatsup: Definition = Definition("hello")

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
