package entidades

import java.sql.Date
import java.time.LocalDate


class Estudio(
    var id_estudio:Int?,
    var nombre_estudio: String?,
    var fundador: String?,
    var fecha_fundacion: Date,
    var beneficio: Float,
    var activo: Boolean
) {
    override fun toString(): String {
        return "| id_estudio=$id_estudio | nombre_estudio='$nombre_estudio' | fundador='$fundador' | fecha_fundacion=$fecha_fundacion | beneficio=$beneficio | activo=$activo |"
    }
}