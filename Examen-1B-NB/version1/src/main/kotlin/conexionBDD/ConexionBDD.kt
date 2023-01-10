package conexionBDD

import java.sql.*


class ConexionBDD {

    companion object {
        private var conexionClass: ConexionBDD? = null
        private var instancia: Connection? = null


        fun getConexion(): Connection? {
            try {
                if (instancia == null) {
                    val servidor = "localhost:5432"
                    val database = "peliculas"
                    val url = "jdbc:postgresql://$servidor/$database"
                    val user = "postgres"
                    val pass = "admin"
                    instancia = DriverManager.getConnection(url, user, pass)
                    println("EXITO")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return instancia
        }

        fun cerrar(rs: ResultSet?) {
            var rs = rs
            try {
                rs!!.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            rs = null
        }

        fun cerrar(pstm: PreparedStatement?) {
            var pstm = pstm
            try {
                pstm!!.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            pstm = null
        }
    }

}