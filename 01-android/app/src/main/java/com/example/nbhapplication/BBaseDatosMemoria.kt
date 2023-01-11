package com.example.nbhapplication

class BBaseDatosMemoria {
    companion object{
        val arregloEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloEntrenador
                .add(
                    BEntrenador(1,"Nicolas","a@a.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador(1,"Vicente","b@b.com")
                )
            arregloEntrenador
                .add(
                    BEntrenador(1,"Carolina","c@c.com")
                )
        }
    }
}