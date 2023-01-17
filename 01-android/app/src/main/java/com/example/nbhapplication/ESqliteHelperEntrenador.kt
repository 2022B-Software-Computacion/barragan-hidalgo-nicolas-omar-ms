package com.example.nbhapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador (
    contexto: Context?,//THIS
) : SQLiteOpenHelper(
    contexto,
    "moviles",//nombre BDD
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun crearEntrenador(
        nombre:String,
        descripcion:String
    ):Boolean{
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre",nombre)
        valoresAGuardar.put("descripcion",descripcion)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "Entrenador", //NOMBRE DE FILA
                null,
                valoresAGuardar //valores
            )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarEntrenador(id: Int):Boolean{
        // val conexionEscritura = this.writedatabase
        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
            .delete(
                "Entrenador", //Nombre tabla
                "id=?", //Consulta Where
                arrayOf(
                    id.toString()
                ) //Parametros
            )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1

    }

    fun actualizarEntrenador(
        nombre: String,
        descripcion:String,
        idActualizar: Int
    ):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre",nombre)
        valoresAActualizar.put("descripcion",descripcion)
        val resultadoActualizacion = conexionEscritura
            .update(
                "ENTRENADOR", //nombre de la tabla
                valoresAActualizar, //valores a actualizar
                "id=?",
                arrayOf(
                    idActualizar.toString()
                )// Parametros clausulas Where
            )
        conexionEscritura.close()
        return resultadoActualizacion != -1

    }

    fun consultarEntrenador(id:Int):BEntrenador{
        val baseDatosLectura = readableDatabase
        val scriptConsultarUsuario = "SELECT * FROM ENTRENADOR WHERE ID = ?"
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultarUsuario,
            arrayOf(
                id.toString()
            )
        )
        //Logica de busqueda

        val existeUsuario = resultadoConsultaLectura.moveToFirst()
        val usuarioEncontrado = BEntrenador(0,"","")
        val arreglo = arrayListOf<BEntrenador>()

        if(existeUsuario){
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val descripcion =
                    resultadoConsultaLectura.getString(2)
                if(id!=null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                    arreglo.add(usuarioEncontrado)
                }
            }while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return usuarioEncontrado
    }
}
