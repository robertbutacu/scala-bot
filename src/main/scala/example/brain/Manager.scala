package example.brain

import bot.learn.RepliesLearner._
import bot.memory.SpeakingKnowledge
import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  lazy val masterBrain: SpeakingKnowledge = learn(SpeakingKnowledge(), List(jobs, ages, greetings).flatten)
}
