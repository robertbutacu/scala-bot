package example.brain.definitions

import bot.trie.definition.{Definition, Synonym}

import scala.util.matching.Regex

object Definitions {

  implicit def convert(s: String): Regex = s.r
  implicit def convert(d: Definition): Synonym = Synonym(d)

  lazy val underage = Definition("underage", Set(minor))
  lazy val minor = Definition("minor", Set(underage))

  lazy val old = Definition("age", Set(ageOld))
  lazy val ageOld = Definition("age-old", Set(old))

  lazy val passionate = Definition("passionate", Set(ardent, keen))
  lazy val ardent = Definition("ardent", Set(passionate, keen))
  lazy val keen = Definition("keen", Set(passionate, ardent))

  lazy val greetings = Definition("greetings", Set(hi, hello, whatsup))
  lazy val hi = Definition("hi", Set(greetings, hello, whatsup))
  lazy val hello = Definition("hello", Set(greetings, hi, whatsup))
  lazy val whatsup = Definition("hello", Set(greetings, hi, hello))
}
