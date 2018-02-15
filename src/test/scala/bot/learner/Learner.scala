package bot.learner

import bot.handler.MessageHandler
import example.brain.modules.Attributes
import org.scalatest.FlatSpec

class Learner extends FlatSpec with Attributes with MessageHandler {
  def ageReply(): Set[String] = {
    Set("Ok", "Test")
  }
}
