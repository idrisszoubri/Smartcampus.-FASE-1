package smartcampus

object Main {

  // Damos nombre a los indices para saber que representa cada posicion de una fila de trips.csv.
  val TripId = 0
  val Origin = 2
  val UserType = 6
  val VehicleType = 7
  val Status = 9

  // Damos tambien nombre a las posiciones que utilizamos de stations.csv.
  val StationId = 0
  val StationName = 1

  def main(args: Array[String]): Unit = {

    // Cargamos los viajes y las estaciones utilizando el metodo readRows creado en esta fase.
    val rows: List[List[String]] = Csv.readRows("datos/trips.csv")
    val stationRows: List[List[String]] = Csv.readRows("datos/stations.csv")

    // Calculamos el numero total de viajes.
    val totalTrips = rows.size

    // Contamos los viajes que tienen estado completed.
    val completedTrips = rows.count { row =>
      row(Status) == "completed"
    }

    // Contamos los viajes que tienen estado cancelled.
    val cancelledTrips = rows.count { row =>
      row(Status) == "cancelled"
    }

    // Contamos los viajes realizados por usuarios de tipo student.
    val studentTrips = rows.count { row =>
      row(UserType) == "student"
    }

    // Transformamos las filas para quedarnos solamente con el tipo de vehiculo de cada viaje y eliminamos los valores repetidos y los ordenamos.
    val vehicleTypes: List[String] = rows
      .map(row => row(VehicleType))
      .distinct
      .sorted

    // Para cada tipo de vehiculo contamos cuantos viajes lo utilizan.
    val vehicleCounts: List[(String, Int)] = vehicleTypes.map { vehicleType =>

      val numberOfTrips = rows.count { row =>
        row(VehicleType) == vehicleType
      }

      // Guardamos el tipo de vehiculo junto con su numero de viajes en una tupla.
      (vehicleType, numberOfTrips)
    }

    // Creamos un Map para relacionar cada station_id con el nombre de su estacion.
    val stationById: Map[String, String] = stationRows
      .map { row =>
        row(StationId) -> row(StationName)
      }
      .toMap

    // Contamos cuantos viajes tienen S01 como estacion de origen.
    val tripsFromS01 = rows.count { row =>
      row(Origin) == "S01"
    }

    // Buscamos el nombre de S01 en el Map y usamos "desconocida" si no existe.
    val stationS01Name = stationById.getOrElse("S01", "desconocida")

    // Primero filtramos las filas completas que tienen S02 como estacion de origen.
    val rowsFromS02: List[List[String]] = rows.filter { row =>
      row(Origin) == "S02"
    }

    // Transformamos las filas anteriores para quedarnos solamente con el trip_id.
    val tripIdsFromS02: List[String] = rowsFromS02.map { row =>
      row(TripId)
    }

    // Nos quedamos como maximo con los diez primeros identificadores.
    val firstTripsFromS02: List[String] = tripIdsFromS02.take(10)

    // Mostramos los resultados una vez terminadas las consultas.
    println("Numero total de viajes: " + totalTrips)
    println("Viajes completados: " + completedTrips)
    println("Viajes cancelados: " + cancelledTrips)
    println("Viajes de estudiantes: " + studentTrips)

    println("\nViajes por tipo de vehiculo:")

    // Recorremos los resultados y mostramos cada tipo de vehiculo junto con su cantidad.
    vehicleCounts.foreach { vehicle =>
      println(vehicle._1 + ": " + vehicle._2)
    }

    // Mostramos la consulta de la estacion S01 utilizando tambien su nombre.
    println(
      "\nViajes con origen S01 (" +
        stationS01Name +
        "): " +
        tripsFromS01
    )

    // Mostramos los diez primeros identificadores de viajes que salen desde S02.
    println(
      "Primeros viajes con origen S02: " +
        firstTripsFromS02.mkString(", ")
    )
  }
}