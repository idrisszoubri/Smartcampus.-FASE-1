package smartcampus

object SafeParse {

  // Intentamos convertir un texto a Int y devolvemos None si la conversion no es posible.
  def toIntOption(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case _: NumberFormatException => None
    }
  }

  // Intentamos convertir un texto a Double y devolvemos None si la conversion no es posible.
  def toDoubleOption(s: String): Option[Double] = {
    try {
      Some(s.toDouble)
    } catch {
      case _: NumberFormatException => None
    }
  }
}