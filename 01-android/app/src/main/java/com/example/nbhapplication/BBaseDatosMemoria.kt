package com.example.nbhapplication

class BBaseDatosMemoria {
    companion object{
        val arregloEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloEntrenador
                .add(
                    BEntrenador("Nicolas","a@a.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador("Vicente","b@b.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador("Carolina","c@c.com")
                )
        }
    }
}