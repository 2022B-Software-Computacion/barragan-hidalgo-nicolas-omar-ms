package jdbcDAO

import conexionBDD.ConexionBDD
import dao.PeliculaDAO
import entidades.Pelicula
import java.sql.Date
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.ResultSet;



class JDBCPeliculaDAO: JDBCGenericDAO<Pelicula, Int>(),PeliculaDAO{

    var pelicula: Pelicula? = null

    override fun create(persona: Pelicula) {
        val sql = "INSERT INTO PELICULAS (id_estudio, nombre_pelicula, director, fecha_lanzamiento, puntuacion, clasificacion) VALUES (?,?,?,?,?,?)"
        var pstm: PreparedStatement? = null
        try {
            pstm = ConexionBDD.getConexion()?.prepareStatement(sql)
            pstm?.setInt(1, persona.id_estudio)
            pstm?.setString(2, persona.nombre)
            pstm?.setString(3, persona.director)
            pstm?.setDate(4, persona.fecha_lanzamiento as Date?)
            pstm?.setFloat(5, persona.puntuacion)
            pstm?.setString(6, persona.clasificacion.toString())
            val filas = pstm?.executeUpdate()
            println("Numero de filas afectadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

}