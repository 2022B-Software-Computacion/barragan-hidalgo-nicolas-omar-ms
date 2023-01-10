package entidades

import java.sql.Date


class Pelicula(
    val id_estudio: Int,
    val nombre: String,
    val director: String,
    val fecha_lanzamiento:Date,
    val puntuacion: Float,
    val clasificacion: Char
) {

}