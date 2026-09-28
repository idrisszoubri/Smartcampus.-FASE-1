package smartcampus

import scala.io.Source

object Csv {

  // Leemos todas las lineas del fichero y las guardamos en una lista antes de cerrar el Source.
  def readAllLines(path: String): List[String] = {
    val source = Source.fromFile(path, "UTF-8")

    try {
      source.getLines().toList
    } finally {
      // Cerramos siempre el fichero aunque se produzca algun problema durante la lectura.
      source.close()
    }
  }

  // Separamos una linea por punto y coma conservando tambien los campos vacios finales.
  def split(line: String): List[String] = {
    line.split(";", -1).toList
  }
}