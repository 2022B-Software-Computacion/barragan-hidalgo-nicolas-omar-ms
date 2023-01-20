package archivos

import entidades.Estudio
import entidades.Pelicula
import java.io.OutputStream

class Archivo {

    companion object{
        fun OutputStream.writeCsvPeliculas(peliculas: List<Pelicula>) {
            val writer = bufferedWriter()
            writer.write(""""ID_PELICULA", "ID_ESTUDIO", "NOMBRE","DIRECTOR","FECHA_LANZAMIENTO","PUNTUACION","CLASIFICACION"""")
            writer.newLine()
            peliculas.forEach {
                writer.write("${it.id_pelicula}, ${it.id_estudio}, ${it.nombre}, ${it.director}, ${it.fecha_lanzamiento}, ${it.puntuacion},${it.clasificacion}")
                writer.newLine()
            }
            writer.flush()
        }

        fun OutputStream.writeCsvEstudios(estudios: List<Estudio>) {
            val writer = bufferedWriter()
            writer.write(""""ID_ESTUDIO", "NOMBRE_ESTUDIO","FUNDADOR","FECHA_FUNDACION","BENEFICIO","ACTIVO"""")
            writer.newLine()
            estudios.forEach {
                writer.write("${it.id_estudio}, ${it.nombre_estudio}, ${it.fundador}, ${it.fecha_fundacion}, ${it.beneficio},${it.activo}")
                writer.newLine()
            }
            writer.flush()
        }


    }



}