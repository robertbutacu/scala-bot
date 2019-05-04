import bot.memory.Utils
import cats.Id
import example.Bot
import cats.instances.all._
import example.brain.definitions.Definitions
import bot.memory.storage.Printer.TriePrinter
import scala.util.Try

object Main extends App {
  val bot = Bot[Try](10)

  bot.startDemo

 // println(Utils.findReplacements("underage", List("As", "a", "14", "years", "of", "age", "Im", "underage"), Definitions.get()))
}
