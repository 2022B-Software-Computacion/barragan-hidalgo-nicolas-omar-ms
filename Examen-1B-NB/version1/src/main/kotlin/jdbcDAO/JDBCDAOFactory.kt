package jdbcDAO
import dao.DAOFactory
import dao.PeliculaDAO

class JDBCDAOFactory: DAOFactory(){
    override fun getPeliculaDAO(): PeliculaDAO? {
        return JDBCPeliculaDAO()
    }

}