package example.brain

import bot.trie.SpeakingKnowledge
import bot.learn.RepliesLearner._
import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  lazy val masterBrain: SpeakingKnowledge = learn(SpeakingKnowledge(), List(jobs, ages, greetings).flatten)
}
