package com.xarxa.proyecto_xarxa_mobile.modelos

class Modalidad() {

    var curso : String
    var idModalidad : Int
    var libroCollection : ArrayList<Libro>

    constructor(curso: String, idModalidad: Int, libroCollection: ArrayList<Libro>) : this() {
        this.curso = curso
        this.idModalidad = idModalidad
        this.libroCollection = libroCollection
    }

    init {
        curso = ""
        idModalidad = 0
        libroCollection = arrayListOf()
    }

    override fun toString(): String {
        return "Modalidad(curso='$curso', idModalidad=$idModalidad, libroCollection=$libroCollection)"
    }
}