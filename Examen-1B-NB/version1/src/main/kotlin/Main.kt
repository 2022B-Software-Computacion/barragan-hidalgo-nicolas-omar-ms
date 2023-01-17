import dao.DAOFactory
import entidades.Estudio
import entidades.Pelicula
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun main(args: Array<String>) {
//    val defaultZoneId = ZoneId.systemDefault()
//    val date = LocalDate.parse("2003-10-10", DateTimeFormatter.ISO_DATE)
//    val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
//    val sqlDate = java.sql.Date(dateU.getTime())
    val fecha = formatearFecha("2003-10-10")
    val fecha2 = formatearFecha("1993-12-07")
    val peli = Pelicula(1, 1, "KillBill vol.1", "Quentin Tarantino", fecha, 9.8F, 'R')
    val estudio = Estudio(null, "Marvel Studios", "Stan Lee", fecha2, 555.46F, true)
    //val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
    //print(peliculas)
    //print(DAOFactory.getFactory()?.getEstudioDAO()?.getAll())
    val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
    FileOutputStream("peliculas.csv").apply {
        if (peliculas != null) {
            writeCsvPeliculas(peliculas)
        }
    }

    val estudios = DAOFactory.getFactory()?.getEstudioDAO()?.getAll()
    FileOutputStream("estudios.csv").apply {
        if (estudios != null) {
            writeCsvEstudios(estudios)
        }
    }
}

fun formatearFecha(fecha: String): java.sql.Date {
    val defaultZoneId = ZoneId.systemDefault()
    val date = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE)
    val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    val sqlDate = java.sql.Date(dateU.getTime())
    return sqlDate
}

fun OutputStream.writeCsvPeliculas(peliculas: List<Pelicula>) {
    val writer = bufferedWriter()
    writer.write(""""ID_PELICULA", "ID_ESTUDIO", "NOMBRE","DIRECTOR","FECHA_LANZAMIENTO","PUNTUACION","CLASIFICACION"""")
    writer.newLine()
    peliculas.forEach {
        writer.write("${it.id_pelicula}, ${it.id_estudio}, ${it.nombre}, ${it.director}, ${it.fecha_lanzamiento}, ${it.puntuacion},${it.clasificacion}")
        writer.newLine()
    }
    writer.flush()
}

fun OutputStream.writeCsvEstudios(estudios: List<Estudio>) {
    val writer = bufferedWriter()
    writer.write(""""ID_ESTUDIO", "NOMBRE_ESTUDIO","FUNDADOR","FECHA_FUNDACION","BENEFICIO","ACTIVO"""")
    writer.newLine()
    estudios.forEach {
        writer.write("${it.id_estudio}, ${it.nombre_estudio}, ${it.fundador}, ${it.fecha_fundacion}, ${it.beneficio},${it.activo}")
        writer.newLine()
    }
    writer.flush()
}


