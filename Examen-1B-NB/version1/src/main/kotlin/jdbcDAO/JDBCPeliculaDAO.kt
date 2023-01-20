package jdbcDAO

import conexionBDD.ConexionBDD
import dao.PeliculaDAO
import entidades.Estudio
import entidades.Pelicula
import java.sql.*


class JDBCPeliculaDAO: JDBCGenericDAO <Pelicula, Int>(),PeliculaDAO{

    //METODO CREATE
    override fun create(pelicula: Pelicula) {
        val sql = "INSERT INTO PELICULAS (id_estudio, nombre_pelicula, director, fecha_lanzamiento, puntuacion, clasificacion) VALUES (?,?,?,?,?,?)"
        var pstm: PreparedStatement?
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm?.setInt(1, pelicula.id_estudio)
            pstm?.setString(2, pelicula.nombre)
            pstm?.setString(3, pelicula.director)
            pstm?.setDate(4, pelicula.fecha_lanzamiento as Date?)
            pstm?.setFloat(5, pelicula.puntuacion)
            pstm?.setString(6, pelicula.clasificacion.toString())
            val filas = pstm?.executeUpdate()
            println("Numero de filas afectadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    //METODO DELETE
    override fun delete(id: Int?) {
        val sql = "DELETE FROM PELICULAS WHERE id_pelicula = ?"
        var pstm: PreparedStatement? = null
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm.setInt(1, id!!)
            val filas = pstm.executeUpdate()
            println("Numero de filas ejecutadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        }finally {
            ConexionBDD.cerrar(pstm)
        }
    }

    //METODO UPDATE
    override fun update(p: Pelicula) {
        val sql = "UPDATE PELICULAS SET id_estudio = ?, nombre_pelicula= ?, director= ?, fecha_lanzamiento= ?, puntuacion= ?, clasificacion=? WHERE id_pelicula = ?"

        var pstm: PreparedStatement? = null
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm?.setInt(1, p.id_estudio)
            pstm?.setString(2, p.nombre)
            pstm?.setString(3, p.director)
            pstm?.setDate(4, p.fecha_lanzamiento as Date?)
            pstm?.setFloat(5, p.puntuacion)
            pstm?.setString(6, p.clasificacion.toString())
            p.id_pelicula?.let { pstm?.setInt(7, it) }
            val filas = pstm?.executeUpdate()
            println("Numero de filas ejecutadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            ConexionBDD.cerrar(pstm)
        }
    }

    //METODO READ O BUSCAR POR ID
    override fun getById(id: Int?): Pelicula {
        val sql = "SELECT * FROM  PELICULAS WHERE id_pelicula = ?"
        var rs: ResultSet? = null
        var pstm: PreparedStatement? = null
        var pelicula: Pelicula? = null
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm.setInt(1, id!!)
            rs = pstm.executeQuery()
            while (rs.next()) {
                pelicula = Pelicula(
                    rs.getInt("id_pelicula"),
                    rs.getInt("id_estudio"),
                    rs.getString("nombre_pelicula"),
                    rs.getString("director"),
                    rs.getDate("fecha_lanzamiento"),
                    rs.getFloat("puntuacion"),
                    rs.getString("clasificacion").first()
                )
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            ConexionBDD.cerrar(rs)
            ConexionBDD.cerrar(pstm)
        }
        return pelicula!!
    }

    //METODO READ O VER LISTA
    override fun getAll(): MutableList<Pelicula> {
        var cnn: Connection? = null
        var rs: ResultSet? = null
        val peliculas: MutableList<Pelicula> = ArrayList<Pelicula>()
        // 2.- Conectar a la BDD
        cnn = ConexionBDD.getConexion()
        try {
            rs = cnn?.prepareStatement("SELECT * FROM PELICULAS")?.executeQuery()
            while (rs?.next() == true) {
                val p = Pelicula(
                    rs.getInt(1),
                    rs.getInt(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getDate(5),
                    rs.getFloat(6),
                    rs.getString(7).first()
                )
                peliculas.add(p)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            // 3.- Cerrar
            ConexionBDD.cerrar(rs)
        }
        return peliculas
    }
}