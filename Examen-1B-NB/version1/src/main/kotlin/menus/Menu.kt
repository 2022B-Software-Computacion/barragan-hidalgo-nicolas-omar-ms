package menus

import archivos.Archivo.Companion.writeCsvEstudios
import archivos.Archivo.Companion.writeCsvPeliculas
import dao.DAOFactory
import entidades.Estudio
import entidades.Pelicula
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object Menu {

    val headerEstudio = String.format(
        "%s %20s %19s %19s %19s %16s\n",
        "ID_ESTUDIO",
        "NOMBRE ESTUDIO",
        "FUNDADOR",
        "FECHA_FUNDACION",
        "BENEFICIO",
        "ACTIVO"
    )

    val headerPelicula = String.format(
        "%10s %19s %20s %20s %19s %19s %16s\n",
        "ID_PELICULA",
        "ID_ESTUDIO",
        "NOMBRE_PELICULA",
        "DIRECTOR",
        "FECHA_LANZAMIENTO",
        "PUNTUACION",
        "CLASIFICACION"
    )

    //FUNCIONES PARA MOSTRAR LOS DIFERENTES MENUS
    fun mostrarMenuPrincipal(){
        //MENU DE OPCIONES
        val sn = Scanner(System.`in`)
        var salir = false
        var opcion: Int //Guardaremos la opcion del usuario

        do {
            println("GESTOR DE PELÍCULAS")
            println("1. Películas")
            println("2. Estudios")
            println("3. Salir")
            print("Escoje una opción:")
            opcion = sn.nextInt()
            println("")
            when (opcion) {
                1 -> mostrarMenuPelicula()
                2 -> mostrarMenuEstudio()
                3 -> salir = true
                else -> println("Solo números entre 1 y 3")
            }
        } while (!salir)
    }

    fun mostrarMenuPelicula() {
        val sn = Scanner(System.`in`)
        var salir = false
        var opcion: Int
        do {
            println("\nPELÍCULAS")
            println("1. Crear película")
            println("2. Eliminar película")
            println("3. Actualizar película")
            println("4. Buscar película")
            println("5. Ver lista de peliculas")
            println("6. Guardar lista de peliculas")
            println("7. Regresar")
            print("Escoje una opción:")
            opcion = sn.nextInt()
            println("")
            when (opcion) {
                1 -> {
                    try {
                        println("CREAR PELÍCULA")
                        print("Id del estudio:")
                        var id_estudio = sn.nextInt()
                        print("Nombre:")
                        var nombre = readLine()
                        print("Director:")
                        var director = readLine()
                        print("Fecha de lanzamiento(aaaa-mm-dd):")
                        var fecha_lanz = readLine()
                        print("Puntuación:")
                        var puntuacion = sn.nextFloat()
                        print("Clasificación:")
                        var clasificación: Char = sn.next().first()
                        println("")
                        var nuevaPelicula = Pelicula(
                            null,
                            id_estudio,
                            nombre,
                            director,
                            formatearFecha(fecha_lanz),
                            puntuacion,
                            clasificación
                        )

                        DAOFactory.getFactory()?.getPeliculaDAO()?.create(nuevaPelicula)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                2 -> {
                    try {
                        println("ELIMINAR PELICULA")
                        print("Ingrese el id de la pelicula:")
                        var id_pelicula = sn.nextInt()
                        DAOFactory.getFactory()?.getPeliculaDAO()?.delete(id_pelicula)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                3 -> {
                    try {
                        println("ACTUALIZAR PELÍCULA")
                        print("Id de la pelicula:")
                        var id_pelicula = sn.nextInt()
                        print("Id del estudio:")
                        var id_estudio = sn.nextInt()
                        print("Nombre:")
                        var nombre = readLine()
                        print("Director:")
                        var director = readLine()
                        print("Fecha de lanzamiento(aaaa-mm-dd):")
                        var fecha_lanz = readLine()
                        print("Puntuación:")
                        var puntuacion = sn.nextFloat()
                        print("Clasificación:")
                        var clasificación: Char = sn.next().first()
                        println("")
                        var peliculaActualizada = Pelicula(
                            id_pelicula,
                            id_estudio,
                            nombre,
                            director,
                            formatearFecha(fecha_lanz),
                            puntuacion,
                            clasificación
                        )
                        DAOFactory.getFactory()?.getPeliculaDAO()?.update(peliculaActualizada)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                4 -> {
                    println("BUSCAR PELICULA")
                    try {
                        print("Ingrese el id de la pelicula:")
                        var id_pelicula = sn.nextInt()
                        var pelicula_buscada = DAOFactory.getFactory()?.getPeliculaDAO()?.getById(id_pelicula)
                        if(pelicula_buscada != null){
                            print(headerPelicula)
                            println(pelicula_buscada)
                        }else{
                            println("Error: Película no encontrada")
                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                5 -> {
                    try {
                        println("LISTA DE PELICULAS")
                        var listaPeliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
                        //print(listaPeliculas.toString().substring(1,listaPeliculas.toString().length-1).replace(",",""))
                        if (listaPeliculas != null) {
                            print(headerPelicula)
                            for (item in listaPeliculas){
                                print(item)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                6 -> {
                    println("GUARDAR LISTA DE PELICULAS")
                    try {
                        val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
                        FileOutputStream("peliculas.csv").apply {
                            if (peliculas != null) {
                                writeCsvPeliculas(peliculas)
                                println("Archivo creado!")
                            } else {
                                println("Error al crear archivo")
                            }
                        }
                    } catch (e: Exception) {
                        print(e)
                    }
                }
                7 -> salir = true
                else -> println("Solo números entre 1 y 7")
            }
        } while (!salir)
    }

    fun mostrarMenuEstudio() {
        val sn = Scanner(System.`in`)
        var salir = false
        var opcion: Int
        do {
            println("\nESTUDIOS DE PELICULAS")
            println("1. Crear estudio")
            println("2. Eliminar estudio")
            println("3. Actualizar estudio")
            println("4. Buscar estudio")
            println("5. Ver lista de estudios")
            println("6. Guardar lista de estudios")
            println("7. Regresar")
            print("Escoje una opción:")
            opcion = sn.nextInt()
            println("")
            when (opcion) {
                1 -> {
                    try {
                        println("CREAR ESTUDIO")
                        print("Nombre del estudio:")
                        var nombre = readLine()
                        print("Fundador:")
                        var fundador = readLine()
                        print("Fecha de fundacion(aaaa-mm-dd):")
                        var fecha_lanz = readLine()
                        print("Benefifico(Millones):")
                        val puntuacion = sn.nextFloat()
                        print("Actividad:")
                        val activo = sn.nextBoolean()

                        var nuevoEstudio = Estudio(
                            null,
                            nombre,
                            fundador,
                            formatearFecha(fecha_lanz),
                            puntuacion,
                            activo
                        )

                        DAOFactory.getFactory()?.getEstudioDAO()?.create(nuevoEstudio)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                2 -> {
                    try {
                        println("ELIMINAR ESTUDIO")
                        print("Ingrese el id del estudio:")
                        var id_estudio = sn.nextInt()

                        DAOFactory.getFactory()?.getEstudioDAO()?.delete(id_estudio)
                    } catch (e: Exception) {
                        println(e)
                    }
                }
                3 -> {
                    println("ACTUALIZAR ESTUDIO")
                    print("Ingrese el id del estudio:")
                    var id_estudio = sn.nextInt()
                    print("Nombre del estudio:")
                    var nombre = readLine()
                    print("Fundador:")
                    var fundador = readLine()
                    print("Fecha de fundacion(aaaa-mm-dd):")
                    var fecha_lanz = readLine()
                    print("Benefifico(Millones):")
                    var puntuacion = sn.nextFloat()
                    print("Actividad:")
                    var activo = sn.nextBoolean()

                    var nuevoEstudio = Estudio(
                        null,
                        nombre,
                        fundador,
                        formatearFecha(fecha_lanz),
                        puntuacion,
                        activo
                    )

                    DAOFactory.getFactory()?.getEstudioDAO()?.update(nuevoEstudio)
                }
                4 -> {
                    println("BUSCAR ESTUDIO")
                    print("Ingrese el id del estudio a buscar:")
                    var id_estudio = sn.nextInt()
                    val estudio_buscado = DAOFactory.getFactory()?.getEstudioDAO()?.getById(id_estudio)
                    if (estudio_buscado != null){
                        print(headerEstudio)
                        println(estudio_buscado)
                    }else{
                        println("Error: Estudio no encontrado")
                    }
                }
                5 -> {
                    try {
                        println("LISTA DE ESTUDIOS")
                        var listaEstudios = DAOFactory.getFactory()?.getEstudioDAO()?.getAll()
                        if (listaEstudios != null) {
                            print(headerEstudio)
                            for (item in listaEstudios){
                                print(item)
                            }
                        }
                     } catch (e: Exception) {
                        println(e)
                    }
                }
                6 -> {
                    println("GUARDAR LISTA DE ESTUDIOS")
                    val estudios = DAOFactory.getFactory()?.getEstudioDAO()?.getAll()
                    FileOutputStream("estudios.csv").apply {
                        if (estudios != null) {
                            writeCsvEstudios(estudios)
                            println("Archivo creado!")
                        } else {
                            println("Error al crear archivo")
                        }
                    }
                }
                7 -> salir = true
                else -> println("Solo números entre 1 y 7")
            }
        } while (!salir)
    }

    //FUNCION PARA CONVERTIR STRING A DATE TIPO SQL
    fun formatearFecha(fecha: String?): java.sql.Date {
        val defaultZoneId = ZoneId.systemDefault()
        val date = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE)
        val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
        val sqlDate = java.sql.Date(dateU.getTime())
        return sqlDate
    }
}