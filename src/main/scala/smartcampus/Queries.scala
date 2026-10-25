package smartcampus

object Queries {

  // Usamos Row como un nombre mas claro para representar una fila del CSV.
  type Row = List[String]

  // Guardamos como privados los indices que solo necesitamos dentro de las consultas.
  private val StartTime = 1
  private val Origin = 2
  private val Destination = 3
  private val DurationMin = 4
  private val UserType = 6
  private val VehicleType = 7
  private val CostEur = 8
  private val Status = 9

  // Nos quedamos solamente con los viajes que tienen estado completed.
  def completed(rows: List[Row]): List[Row] = {
    rows.filter(row => row(Status) == "completed")
  }

  // Agrupamos los viajes completados por tipo de vehiculo y contamos cuantos hay de cada tipo.
  def tripsByVehicle(rows: List[Row]): Map[String, Int] = {
    val completedRows = completed(rows)

    val grouped: Map[String, List[Row]] = completedRows.groupBy { row =>
      row(VehicleType)
    }

    grouped.map {
      case (vehicleType, vehicleRows) =>
        vehicleType -> vehicleRows.size
    }
  }

  // Agrupamos los viajes completados por tipo de usuario y calculamos su duracion media.
  def averageDurationByUser(rows: List[Row]): Map[String, Double] = {
    val completedRows = completed(rows)

    val grouped: Map[String, List[Row]] = completedRows.groupBy { row =>
      row(UserType)
    }

    grouped.map {
      case (userType, userRows) =>

        // Conservamos solamente las duraciones que se pueden convertir correctamente a Int.
        val durations: List[Int] = userRows.flatMap { row =>
          SafeParse.toIntOption(row(DurationMin))
        }

        // Si no hay ninguna duracion interpretable devolvemos 0.0 para ese grupo.
        val average =
          if (durations.nonEmpty) durations.sum.toDouble / durations.size
          else 0.0

        userType -> average
    }
  }

  // Reutilizamos el mismo algoritmo para obtener rankings por cualquier columna indicada.
  private def topBy(rows: List[Row], idx: Int, n: Int): List[(String, Int)] = {

    val grouped: Map[String, List[Row]] = rows.groupBy { row =>
      row(idx)
    }

    val counts: Map[String, Int] = grouped.map {
      case (key, groupRows) =>
        key -> groupRows.size
    }

    val countsList: List[(String, Int)] = counts.toList

    val sorted: List[(String, Int)] = countsList.sortBy {
      case (_, count) => -count
    }

    sorted.take(n)
  }

  // Obtenemos las estaciones de origen mas frecuentes.
  def topOrigins(rows: List[Row], n: Int): List[(String, Int)] = {
    topBy(rows, Origin, n)
  }

  // Obtenemos las estaciones de destino mas frecuentes.
  def topDestinations(rows: List[Row], n: Int): List[(String, Int)] = {
    topBy(rows, Destination, n)
  }

  // Agrupamos los viajes completados por dia y sumamos sus ingresos.
  def revenueByDay(rows: List[Row]): Map[String, Double] = {
    val completedRows = completed(rows)

    val grouped: Map[String, List[Row]] = completedRows.groupBy { row =>
      row(StartTime).take(10)
    }

    grouped.map {
      case (date, dayRows) =>

        val revenue = dayRows.map { row =>
          SafeParse.toDoubleOption(row(CostEur)).getOrElse(0.0)
        }.sum

        date -> revenue
    }
  }

  // Buscamos la ruta origen-destino que mas veces aparece entre los viajes completados.
  def mostFrequentRoute(rows: List[Row]): Option[((String, String), Int)] = {
    val completedRows = completed(rows)

    val grouped: Map[(String, String), List[Row]] = completedRows.groupBy { row =>
      (row(Origin), row(Destination))
    }

    val counts: List[((String, String), Int)] = grouped.map {
      case (route, routeRows) =>
        route -> routeRows.size
    }.toList

    counts.sortBy {
      case (_, count) => -count
    }.headOption
  }

  // Clasificamos la hora de inicio de un viaje en una franja horaria.
  def timeBand(startTime: String): String = {
    val hour = startTime.slice(11, 13).toInt

    if (hour >= 6 && hour < 12) "morning"
    else if (hour >= 12 && hour < 18) "afternoon"
    else if (hour >= 18 && hour < 22) "evening"
    else "night"
  }

  // Buscamos la franja horaria con mas viajes completados.
  def busiestTimeBand(rows: List[Row]): Option[(String, Int)] = {
    val completedRows = completed(rows)

    val grouped: Map[String, List[Row]] = completedRows.groupBy { row =>
      timeBand(row(StartTime))
    }

    val counts: List[(String, Int)] = grouped.map {
      case (band, bandRows) =>
        band -> bandRows.size
    }.toList

    counts.sortBy {
      case (_, count) => -count
    }.headOption
  }

  // Recorremos los viajes completados acumulando a la vez los ingresos y el numero de viajes.
  def revenueAndCount(rows: List[Row]): (Double, Int) = {
    completed(rows).foldLeft((0.0, 0)) {
      case ((revenue, count), row) =>

        val cost = SafeParse.toDoubleOption(row(CostEur)).getOrElse(0.0)

        (revenue + cost, count + 1)
    }
  }
}