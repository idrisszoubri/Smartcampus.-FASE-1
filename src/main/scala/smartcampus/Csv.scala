package smartcampus

import scala.io.Source

object Csv {

  // Leemos todas las lineas del fichero y las guardamos en una lista antes de cerrar el Source.
  def readAllLines(path: String): List[String] = {
    val source = Source.fromFile(path, "UTF-8")

    try {
      // Convertimos el Iterator en una List para poder seguir usando las lineas despues de cerrar el fichero.
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

  // Leemos las filas del fichero, quitamos la cabecera y devolvemos cada fila separada en sus campos.
  def readRows(path: String): List[List[String]] = {

    // Reutilizamos el metodo de la fase anterior para leer todas las lineas.
    val lines = readAllLines(path)

    // Quitamos la cabecera, descartamos las lineas completamente vacias y separamos cada fila.
    lines
      .drop(1)
      .filter(line => line.nonEmpty)
      .map(line => split(line))
  }
}