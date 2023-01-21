package entidades

import java.sql.Date


class Pelicula(
    var id_pelicula: Int?,
    var id_estudio: Int,
    var nombre: String?,
    var director: String?,
    var fecha_lanzamiento: Date,
    var puntuacion: Float,
    var clasificacion: Char,
) {
    val strPelicula = String.format(
        "%10d %19d %20s %20s %19s %19f %16c\n",
        id_pelicula,
        id_estudio,
        nombre,
        director,
        fecha_lanzamiento,
        puntuacion,
        clasificacion
    )

    override fun toString(): String {
        return strPelicula
    }
}