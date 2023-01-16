package entidades

import java.sql.Date


class Pelicula(
    var id_pelicula: Int?,
    var id_estudio: Int,
    var nombre: String,
    var director: String,
    var fecha_lanzamiento: Date,
    var puntuacion: Float,
    var clasificacion: Char,
) {

    override fun toString(): String {
        return "| id_pelicula=$id_pelicula | id_estudio=$id_estudio | nombre='$nombre'| director='$director'| fecha_lanzamiento=$fecha_lanzamiento | puntuacion=$puntuacion | clasificacion='$clasificacion' |"
    }
}