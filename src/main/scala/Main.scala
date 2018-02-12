import bot.memory.definition.NodeSimpleWord
import example.brain.Manager
import example.brain.definitions.Definitions

object Main extends App with Manager{
  //Master.tickle().onComplete(s => println(s + "1"))
  //Master.tickle().onComplete(s => println(s + "2"))
  // new Bot().startDemo()
  //Master.kill()
  //Definitions.get().foreach(println)
  clusterizedMasterBrain
}
