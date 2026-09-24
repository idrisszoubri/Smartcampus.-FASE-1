package smartcampus //[cite: 1]

import scala.io.Source //[cite: 1]

object Csv { //[cite: 1]

  // Recibe la ruta, abre el fichero, materializa la lista y garantiza el cierre[cite: 1]
  def readAllLines(path: String): List[String] = { //[cite: 1]
    val source = Source.fromFile(path, "UTF-8") //[cite: 1]
    try {
      // getLines devuelve un Iterator consumible, toList lo materializa en una colección en memoria[cite: 1]
      source.getLines().toList //[cite: 1]
    } finally {
      // El bloque finally es obligatorio para asegurar que el Source se cierre siempre[cite: 1]
      source.close() //[cite: 1]
    }
  }

  // Divide una línea conservando los campos vacíos finales gracias al -1[cite: 1]
  def split(line: String): List[String] = { //[cite: 1]
    line.split(";", -1).toList //[cite: 1]
  }
}