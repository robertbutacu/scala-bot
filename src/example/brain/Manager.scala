package example.brain

import example.brain.modules.MasterModule

import scala.bot.learn.Learner._
import scala.bot.trie.Trie

trait Manager extends MasterModule {
  lazy val masterBrain: Trie = learn(Trie(), List(jobs, ages, greetings))
}
