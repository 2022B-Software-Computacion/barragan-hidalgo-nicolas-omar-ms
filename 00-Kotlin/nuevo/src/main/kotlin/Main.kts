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

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)

    Suma.elevarALCuadrado(2)
    Suma.historialSumas
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

//Clases java
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno:Int
        dos: Int
    ){ //Bloque de codigo constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializado")
    }
}

//Clases en Kotlin
abstract class Numeros( //Constructor
    //public var uno: Int, // Parametro
    protected val numeroUno: Int,
    protected val numeroDos: Int,
){
   //var cedula: String="";
    //protected val numeroUno: Int
    init{ //Bloque de codigo constructor
        //this.numeroUno = uno
        this.numeroUno
        numeroUno
        this.numeroDos
        numeroDos
        println("Inicializado")
    }
}

//Subclases y constructores
class Suma( //Constructor
    uno: Int, //Parametro
    dos: Int  //Parametro
):Numeros(uno,dos){
    init{ //Bloque constructor
        this.numeroUno
        this.numeroDos
    }
    constructor( //Segundo constructor
        uno: Int?,
        dos: Int?
    ):this( //Llamada a constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    ){
    }
    constructor( //Tercer constructor
        uno: Int,
        dos: Int?
    ):this( //Llamada a constructor primario
        uno,
        if (dos == null) 0 else dos
    ){
    }
    constructor( //Cuarto constructor
        uno: Int?,
        dos: Int?
    ):this( //Llamada a constructor primario
        if (uno == null) 0 else uno,
        dos
    ){
    }

    public fun sumar(): Int{
        return numeroUno + numeroDos
    }

    companion object { //Atributos y metodos "Compartidos" entre instancias
        // entre las instancias
        val pi = 3.14
        fun elevarALCuadrado(num: Int){
            return num*num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}


main()