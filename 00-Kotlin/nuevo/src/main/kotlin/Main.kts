import java.util.*

//Main.kts
fun main(){
    println("Hola Mundo")

    //INMUTABLES (No reasignar) =
    val inmutable: String = "Nicolas";

    //MUTABLES (Re Asignar) =
    var mutable: String = "Barragan"
    mutable = "Hidalgo"

    //val > var

    //Sintaxis Duck typing
    val ejemploVariable = "Ejemplo"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()

    //Variables primitivas
    val nombreEstudiante: String = "Nicolas Barragan"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true

    //Clases JAVA
    val fechaNacimiento: Date = Date()

    //Condicionales
    if(true){

    }else{
    }
    //switch no existe
    val estadoCivilWhen = "S"
    when(estadoCivilWhen){
        ("S")-> {
            println("Acercase")
        }
        "C" -> {
            println("Alejarse")
        }
        "UN" -> println("Hablar")
        else -> println("No reconocido")
    }
    val coqueteo = if(estadoCivilWhen == "S") "SI" else "NO"
}

//void imprimirNombre(String nombre){}
//Unit = Void
fun imprimirNombre(nombre:String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo:Double, //Requerido
    tasa:Double = 12.00, //Opcional (Defecto)
    bonoEspecial: Double? = null, //Opcional(null) -> nullable
): Double{
    if(bonoEspecial == null){
        return sueldo * (1000/tasa)
    } else {
        return sueldo * (100/tasa) + bonoEspecial
    }
}

main()