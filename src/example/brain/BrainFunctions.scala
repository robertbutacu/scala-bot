package example.brain

object BrainFunctions {

  def ageReply(string: Option[String]): String ={
    println(string)
    string match {
      case None      => "You've never told me your age"
      case Some(age) => provideAge(age)
    }
  }


  def provideAge(age: String): String =
    age match {
      case young if young.toInt > 5 && young.toInt < 15 => "You're young"
      case teen  if teen.toInt  < 21 => "You're a teen"
      case semiAdult if semiAdult.toInt < 25 => "You're a semiadult"
      case adult if adult.toInt < 50 => "You're an adult"
      case old   if old.toInt < 90   => "You're old"
      case _                         => "I believe there might be something wrong with your age"
    }
}
