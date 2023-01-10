package jdbcDAO

import dao.GenericDAO

open class JDBCGenericDAO<T,ID>: GenericDAO<T, ID> {
    override fun getById(id: ID?): T {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<T>? {
        TODO("Not yet implemented")
    }

    override fun delete(id: ID?) {
        TODO("Not yet implemented")
    }

    override fun update(p: T) {
        TODO("Not yet implemented")
    }

    override fun create(p: T) {
        TODO("Not yet implemented")
    }
}