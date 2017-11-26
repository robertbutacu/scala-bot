package example.brain

import bot.trie.Trie
import bot.learn.RepliesLearner._
import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  lazy val masterBrain: Trie = learn(Trie(), List(jobs, ages, greetings).flatten)
}
