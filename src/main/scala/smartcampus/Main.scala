package smartcampus

object Main {

  // Ejecutamos desde aqui la carga de datos, las consultas, las pruebas y la presentacion de resultados.
  def main(args: Array[String]): Unit = {

    // Cargamos las filas del fichero una sola vez y dejamos las consultas en el objeto Queries.
    val rows: List[Queries.Row] = Csv.readRows("datos/trips.csv")

    // Llamamos a las distintas consultas y guardamos sus resultados antes de mostrarlos.
    val completedRows = Queries.completed(rows)
    val tripsByVehicle = Queries.tripsByVehicle(rows)
    val averageDurationByUser = Queries.averageDurationByUser(rows)
    val topOrigins = Queries.topOrigins(rows, 3)
    val topDestinations = Queries.topDestinations(rows, 3)
    val revenueByDay = Queries.revenueByDay(rows)
    val mostFrequentRoute = Queries.mostFrequentRoute(rows)
    val busiestTimeBand = Queries.busiestTimeBand(rows)
    val revenueAndCount = Queries.revenueAndCount(rows)

    // Creamos una coleccion pequena para comprobar las consultas sin depender del CSV completo.
    val testRows: List[Queries.Row] = List(
      List("T1", "2026-10-01T08:00:00", "S01", "S02", "10", "1.0", "student", "bike", "2.0", "completed", "ok"),
      List("T2", "2026-10-01T14:00:00", "S01", "S03", "20", "2.0", "student", "bike", "3.0", "completed", "ok"),
      List("T3", "2026-10-01T19:00:00", "S03", "S01", "15", "1.5", "subscriber", "scooter", "1.5", "cancelled", "ok"),
      List("T4", "2026-10-02T18:00:00", "S02", "S01", "30", "3.0", "subscriber", "ebike", "4.0", "completed", "ok")
    )

    // Comprobamos completed con los datos pequenos de prueba.
    assert(Queries.completed(testRows).size == 3)

    // Comprobamos una consulta por tipo de vehiculo.
    assert(Queries.tripsByVehicle(testRows) == Map("bike" -> 2, "ebike" -> 1))

    // Comprobamos un ranking de estaciones de origen.
    assert(Queries.topOrigins(testRows, 1) == List(("S01", 2)))

    // Comprobamos que foldLeft acumula correctamente ingresos y numero de viajes completados.
    assert(Queries.revenueAndCount(testRows) == (9.0, 3))

    // Mostramos el numero total de viajes que tienen estado completed.
    println("Numero de viajes completados: " + completedRows.size)

    // Mostramos cuantos viajes completados corresponden a cada tipo de vehiculo.
    println("\nViajes completados por tipo de vehiculo:")

    // Ordenamos los tipos de vehiculo por su nombre y recorremos el resultado para mostrarlo.
    tripsByVehicle.toList.sortBy(_._1).foreach {
      case (vehicleType, count) =>
        println(vehicleType + ": " + count)
    }

    // Mostramos la duracion media calculada para cada tipo de usuario.
    println("\nDuracion media por tipo de usuario:")

    // Ordenamos los tipos de usuario por su nombre y mostramos la media de cada uno.
    averageDurationByUser.toList.sortBy(_._1).foreach {
      case (userType, average) =>
        println(userType + ": " + average)
    }

    // Mostramos las tres estaciones de origen con mayor numero de viajes.
    println("\nTop 3 estaciones de origen:")

    // Recorremos el ranking de origenes y mostramos cada estacion junto con su numero de viajes.
    topOrigins.foreach {
      case (station, count) =>
        println(station + ": " + count)
    }

    // Mostramos las tres estaciones de destino con mayor numero de viajes.
    println("\nTop 3 estaciones de destino:")

    // Recorremos el ranking de destinos y mostramos cada estacion junto con su numero de viajes.
    topDestinations.foreach {
      case (station, count) =>
        println(station + ": " + count)
    }

    // Mostramos los ingresos obtenidos en cada dia.
    println("\nIngresos por dia:")

    // Ordenamos los dias por fecha y mostramos los ingresos correspondientes a cada uno.
    revenueByDay.toList.sortBy(_._1).foreach {
      case (date, revenue) =>
        println(date + ": " + revenue)
    }

    // Comprobamos si existe una ruta mas frecuente antes de mostrar su resultado.
    mostFrequentRoute match {

      // Si existe una ruta, mostramos su origen, destino y numero de viajes.
      case Some(((origin, destination), count)) =>
        println(
          "\nRuta mas frecuente: " +
            origin +
            " -> " +
            destination +
            " (" +
            count +
            " viajes)"
        )

      // Si no existe ninguna ruta, mostramos un mensaje indicandolo.
      case None =>
        println("\nNo hay una ruta mas frecuente.")
    }

    // Comprobamos si existe una franja horaria con viajes antes de mostrar el resultado.
    busiestTimeBand match {

      // Si existe, mostramos el nombre de la franja y su numero de viajes.
      case Some((band, count)) =>
        println(
          "Franja horaria con mas viajes: " +
            band +
            " (" +
            count +
            " viajes)"
        )

      // Si no existe ninguna franja con viajes, mostramos un mensaje indicandolo.
      case None =>
        println("No hay una franja horaria con viajes.")
    }

    // Mostramos los ingresos totales acumulados con foldLeft.
    println(
      "Ingresos totales de viajes completados: " +
        revenueAndCount._1
    )

    // Mostramos el numero total de viajes completados acumulado con foldLeft.
    println(
      "Numero de viajes completados contado con foldLeft: " +
        revenueAndCount._2
    )

    // Indicamos que todas las comprobaciones anteriores se han realizado correctamente.
    println("\nTodos los assert se han superado correctamente.")
  }
}