package example.brain

import example.brain.modules.MasterModule

trait Manager extends MasterModule {
  lazy val masterBrain: Templates = learn(Map[String, List[String]]().empty,
    List(ages, greetings, jobs))
}
