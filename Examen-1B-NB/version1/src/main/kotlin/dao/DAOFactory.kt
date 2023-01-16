package dao

import jdbcDAO.JDBCDAOFactory

abstract class DAOFactory {
    companion object{
        protected var factory = JDBCDAOFactory()

        open fun getFactory(): DAOFactory? = factory
    }

    abstract fun getPeliculaDAO(): PeliculaDAO?

    abstract fun getEstudioDAO(): EstudioDAO?

}