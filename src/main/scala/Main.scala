package smartcampus //[cite: 1]

import scala.io.Source //[cite: 1]

object Csv { //[cite: 1]

  //Recibimos  ruta, abrimos fichero, materializamos listam, garantizamos cierre[cite: 1]
  def readAllLines(path: String): List[String] = { //[cite: 1]
    val source = Source.fromFile(path, "UTF-8") //[cite: 1]
    try {
      //getLines devuelve un Iterator consumible, toList lo materializa en una colección en memoria[cite: 1]
      source.getLines().toList //[cite: 1]
    } finally {
      source.close() //[cite: 1]
    }
  }

  // Dividimos una línea conservando los campos vacíos finales gracias al -1[cite: 1]
  def split(line: String): List[String] = { //[cite: 1]
    line.split(";", -1).toList //[cite: 1]
  }
}