package dao

interface GenericDAO <T,ID> {

    fun getById(id: ID?): T

    fun getAll(): List<T>?

    fun create(p: T)

    fun update(p: T)

    fun delete(id: ID?)

}