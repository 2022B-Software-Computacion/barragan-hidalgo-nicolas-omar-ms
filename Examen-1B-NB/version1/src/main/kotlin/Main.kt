import archivos.Archivo
import archivos.Archivo.Companion.writeCsvEstudios
import archivos.Archivo.Companion.writeCsvPeliculas
import dao.DAOFactory
import entidades.Estudio
import entidades.Pelicula
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun main(args: Array<String>) {

    val fecha = formatearFecha("2003-10-10")
    val fecha2 = formatearFecha("1993-12-07")
    val peli = Pelicula(1, 1, "KillBill vol.1", "Quentin Tarantino", fecha, 9.8F, 'R')
    val estudio = Estudio(null, "Marvel Studios", "Stan Lee", fecha2, 555.46F, true)
    //val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
    //print(peliculas)
    //print(DAOFactory.getFactory()?.getEstudioDAO()?.getAll())
//    val peliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
//    FileOutputStream("peliculas.csv").apply {
//        if (peliculas != null) {
//            writeCsvPeliculas(peliculas)
//        }
//    }
//
//    val estudios = DAOFactory.getFactory()?.getEstudioDAO()?.getAll()
//    FileOutputStream("estudios.csv").apply {
//        if (estudios != null) {
//            writeCsvEstudios(estudios)
//        }
//    }
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

//FUNCIONES PARA EL MENU DE OPCIONES
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
                    println(pelicula_buscada)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            5 -> {
                try {
                    println("LISTA DE PELICULAS")
                    var listaPeliculas = DAOFactory.getFactory()?.getPeliculaDAO()?.getAll()
                    print(listaPeliculas.toString().substring(1,listaPeliculas.toString().length-1).replace(",",""))
                } catch (e: Exception) {
                    println()
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
                print("Benefifico(Mill):")
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
                println(estudio_buscado)
            }
            5 -> {
                try {
                    println("LISTA DE ESTUDIOS")
                    var listaEstudios = DAOFactory.getFactory()?.getEstudioDAO()?.getAll()
                    println(listaEstudios.toString().substring(1,listaEstudios.toString().length-1).replace(",",""))
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

//FUNCIONES PARA MOSTRAR
fun mostrarTablaPeliculas(){

}

fun mostrarTablaEstudios(){

}

//FUNCION PARA CONVERTIR STRING A DATE TIPO SQL
fun formatearFecha(fecha: String?): java.sql.Date {
    val defaultZoneId = ZoneId.systemDefault()
    val date = LocalDate.parse(fecha, DateTimeFormatter.ISO_DATE)
    val dateU: Date = Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    val sqlDate = java.sql.Date(dateU.getTime())
    return sqlDate
}





