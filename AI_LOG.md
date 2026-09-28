# Registro de IAG utilizada para consultar dudas.

# AI_LOG

## Consulta 1

Fecha: 21/09/2026

Herramienta: ChatGPT

Uso: Consultamos cómo organizar la estructura del proyecto de la Fase 1 y dónde debían estar `Csv.scala` y `Main.scala`.

Código afectado: Estructura del proyecto, `Csv.scala` y `Main.scala`.

Verificación: Comprobamos que la estructura coincidía con la indicada en el enunciado y que `sbt compile` terminaba correctamente.


## Consulta 2

Fecha: 21/09/2026

Herramienta: ChatGPT

Uso: Consultamos por qué era necesario convertir el `Iterator` de `getLines()` en una `List` antes de cerrar el `Source`.

Código afectado: `Csv.scala`.

Verificación: Comprobamos que el fichero se leía correctamente y que el `Source` se cerraba después de materializar las líneas.


## Consulta 3

Fecha: 23/09/2026

Herramienta: ChatGPT

Uso: Consultamos cómo tratar el BOM de los ficheros CSV y por qué utilizábamos `split(";", -1)`.

Código afectado: `Main.scala` y `Csv.scala`.

Verificación: Ejecutamos el programa y comprobamos que la cabecera se mostraba correctamente, que las filas tenían el número de campos esperado y que no había filas con estructura incorrecta.