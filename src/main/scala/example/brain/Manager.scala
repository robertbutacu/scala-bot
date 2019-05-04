package example.brain

import bot.learn.RepliesLearner._
import bot.memory.Trie
import bot.memory.definition.SimpleWord
import example.brain.definitions.Definitions
import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  val masterBrain: Trie = learn(Trie(SimpleWord("".r)), List(jobs, ages, greetings).flatten, Definitions.get())
}
