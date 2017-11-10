package example.brain

import example.brain.modules.MasterModule

import scala.bot.learn.RepliesLearner._
import scala.bot.trie.Trie

trait Manager extends MasterModule {
  lazy val masterBrain: Trie = learn(Trie(), List(jobs, ages, greetings).flatten)
}
