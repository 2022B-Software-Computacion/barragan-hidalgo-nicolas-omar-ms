import dao.DAOFactory
import entidades.Estudio
import entidades.Pelicula
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
    val peli = Pelicula(1,1,"KillBill vol.1","Quentin Tarantino",fecha, 9.8F,'R')
    val estudio = Estudio(null,"Marvel Studios","Stan Lee",fecha2, 555.46F,true)
    //val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
    //print(peliculas)
    print(DAOFactory.getFactory()?.getEstudioDAO()?.getAll())
}

fun formatearFecha(fecha: String):java.sql.Date{
    val defaultZoneId = ZoneId.systemDefault()
    val date = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE)
    val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    val sqlDate = java.sql.Date(dateU.getTime())
    return sqlDate
}