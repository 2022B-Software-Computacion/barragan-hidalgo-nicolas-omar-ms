package jdbcDAO

import conexionBDD.ConexionBDD
import dao.EstudioDAO
import entidades.Estudio
import java.sql.*


class JDBCEstudioDAO: JDBCGenericDAO <Estudio, Int>(), EstudioDAO {

    //METODO CREATE
    override fun create(estudio: Estudio) {
        val sql = "INSERT INTO ESTUDIO_PELICULAS (nombre_estudio, fundador, fecha_fundacion, beneficio, activo) VALUES (?,?,?,?,?)"
        var pstm: PreparedStatement?
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm?.setString(1, estudio.nombre_estudio)
            pstm?.setString(2, estudio.fundador)
            pstm?.setDate(3, estudio.fecha_fundacion as Date?)
            pstm?.setFloat(4, estudio.beneficio)
            pstm?.setBoolean(5, estudio.activo)
            val filas = pstm?.executeUpdate()
            println("Numero de filas afectadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    //METODO DELETE
    override fun delete(id: Int?) {
        val sql = "DELETE FROM ESTUDIO_PELICULAS WHERE id_estudio = ?"
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
    override fun update(estudio: Estudio) {
        val sql = "UPDATE ESTUDIO_PELICULAS SET nombre_estudio= ?, fundador= ?, fecha_fundacion= ?, beneficio= ?, activo=? WHERE id_estudio = ?"

        var pstm: PreparedStatement? = null
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm?.setString(1, estudio.nombre_estudio)
            pstm?.setString(2, estudio.fundador)
            pstm?.setDate(3, estudio.fecha_fundacion as Date?)
            pstm?.setFloat(4, estudio.beneficio)
            pstm?.setBoolean(5, estudio.activo)
            estudio.id_estudio?.let { pstm?.setInt(6, it) }
            val filas = pstm?.executeUpdate()
            println("Numero de filas ejecutadas: $filas")
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            ConexionBDD.cerrar(pstm)
        }
    }

    //METODO READ O BUSCAR POR ID
    override fun getById(id: Int?): Estudio {
        val sql = "SELECT * FROM  ESTUDIO WHERE id_estudio = ?"
        var rs: ResultSet? = null
        var pstm: PreparedStatement? = null
        var estudio: Estudio? = null
        try {
            pstm = ConexionBDD.getConexion()!!.prepareStatement(sql)
            pstm.setInt(1, id!!)
            rs = pstm.executeQuery()
            while (rs.next()) {
                estudio = Estudio(
                    rs.getInt("id_estudio"),
                    rs.getString("nombre_estudio"),
                    rs.getString("fundador"),
                    rs.getDate("fecha_fundacion"),
                    rs.getFloat("benficio"),
                    rs.getBoolean("activo")
                )
            }

        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            ConexionBDD.cerrar(rs)
            ConexionBDD.cerrar(pstm)
        }
        return estudio!!
    }

    //METODO READ O VER LISTA
    override fun getAll(): MutableList<Estudio> {
        var cnn: Connection? = null
        var rs: ResultSet? = null
        val estudios: MutableList<Estudio> = ArrayList<Estudio>()
        // 2.- Conectar a la BDD
        cnn = ConexionBDD.getConexion()
        try {
            rs = cnn?.prepareStatement("SELECT * FROM ESTUDIO_PELICULAS")?.executeQuery()
            while (rs?.next() == true) {
                val e = Estudio(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getDate(4),
                    rs.getFloat(5),
                    rs.getBoolean(6))
                estudios.add(e)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            // 3.- Cerrar
            ConexionBDD.cerrar(rs)
        }
        return estudios
    }
}