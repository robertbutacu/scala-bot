package example.brain

import example.brain.modules.MasterModule

trait Manager extends MasterModule{
  var masterBrain: Templates = learn(Map[String, List[String]]().empty, ages)


}
