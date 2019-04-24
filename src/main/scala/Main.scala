import cats.Id
import example.Bot
import cats.instances.all._
import bot.memory.storage.Printer.TriePrinter
object Main extends App {
  val bot = Bot[Id](10)

  bot.masterBrain.print()
  bot.startDemo
}
