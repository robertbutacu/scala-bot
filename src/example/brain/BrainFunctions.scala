package example.brain

object BrainFunctions {

  def ageReply(string: Option[String] = None): Set[String] =
    string match {
      case None      => Set("You've never told me your age")
      case Some(age) => provideAge(age)
    }


  def provideAge(age: String): Set[String] =
    age match {
      case young if young.toInt > 5 && young.toInt < 15 => Set("You're young")
      case teen  if teen.toInt  < 21 => Set("You're a teen")
      case semiAdult if semiAdult.toInt < 25 => Set("You're a semiadult")
      case adult if adult.toInt < 50 => Set("You're an adult")
      case old   if old.toInt < 90   => Set("You're old")
      case _                         => Set("I believe there might be something wrong with your age")
    }
}
