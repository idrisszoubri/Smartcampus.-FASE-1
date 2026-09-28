package smartcampus

object Main {

  def main(args: Array[String]): Unit = {

    // Leemos todas las lineas del fichero de viajes.
    val tripsLines = Csv.readAllLines("datos/trips_sample.csv")

    // Separamos la cabecera de las filas y eliminamos el posible BOM inicial.
    val tripsHeader = tripsLines.head.stripPrefix("\uFEFF")
    val tripsRows = tripsLines.drop(1)

    // Separamos la cabecera en sus diferentes campos.
    val tripsHeaderFields = Csv.split(tripsHeader)

    println("Cabecera de trips_sample.csv:")
    println(tripsHeaderFields.mkString(" | "))
    println("Numero de columnas: " + tripsHeaderFields.size)

    // Mostramos como maximo las cinco primeras filas y comprobamos cuantos campos tiene cada una.
    println("\nPrimeras filas de trips_sample.csv:")

    tripsRows.take(5).foreach { row =>
      println("Fila: " + row)

      val fields = Csv.split(row)
      println("Numero de campos: " + fields.size)
    }

    // Comprobamos cuantas filas tienen un numero de campos distinto al de la cabecera.
    val incorrectRows = tripsRows.count { row =>
      Csv.split(row).size != tripsHeaderFields.size
    }

    println("\nFilas con estructura incorrecta: " + incorrectRows)

    // Reutilizamos el mismo lector para cargar el fichero de estaciones.
    val stationsLines = Csv.readAllLines("datos/stations.csv")

    // Separamos tambien su cabecera de las filas y eliminamos el posible BOM.
    val stationsHeader = stationsLines.head.stripPrefix("\uFEFF")
    val stationsRows = stationsLines.drop(1)

    // Mostramos el identificador y el nombre de cada estacion.
    println("\nEstaciones:")

    stationsRows.foreach { row =>
      val fields = Csv.split(row)
      println(fields(0) + " -> " + fields(1))
    }
  }
}