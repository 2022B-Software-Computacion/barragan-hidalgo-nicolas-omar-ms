import dao.DAOFactory
import entidades.Pelicula
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun main(args: Array<String>) {
    val defaultZoneId = ZoneId.systemDefault()
    val date = LocalDate.parse("2003-10-10", DateTimeFormatter.ISO_DATE)
    val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    val sqlDate = java.sql.Date(dateU.getTime())
    val peli = Pelicula(1,"KillBill","Quentin Tarantino",sqlDate, 9.8F,'R')
    DAOFactory.getFactory()?.getPeliculaDAO()?.create(peli)

}

fun menu(){

}