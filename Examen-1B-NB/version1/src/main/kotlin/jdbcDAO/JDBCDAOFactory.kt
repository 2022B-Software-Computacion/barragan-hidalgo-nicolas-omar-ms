package jdbcDAO
import dao.DAOFactory
import dao.EstudioDAO
import dao.PeliculaDAO

class JDBCDAOFactory: DAOFactory(){
    override fun getPeliculaDAO(): PeliculaDAO? {
        return JDBCPeliculaDAO()
    }

    override fun getEstudioDAO(): EstudioDAO? {
        return JDBCEstudioDAO()
    }

}