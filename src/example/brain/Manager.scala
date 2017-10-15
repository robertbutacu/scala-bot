package example.brain

import example.brain.modules.MasterModule

import scala.bot.handler.Attribute
import scala.bot.trie.Trie
import scala.util.matching.Regex

trait Manager extends MasterModule {
  lazy val masterBrain: Trie = Trie()
}
