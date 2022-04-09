package com.xarxa.proyecto_xarxa_mobile.modelos

class Modalidad() {

    var curso: String
    var idModalidad: Int
    var nombre: String
    var libroCollection: ArrayList<Libro>

    constructor(
        curso: String,
        idModalidad: Int,
        nombre: String,
        libroCollection: ArrayList<Libro>
    ) : this() {
        this.curso = curso
        this.idModalidad = idModalidad
        this.nombre = nombre
        this.libroCollection = libroCollection
    }

    init {
        curso = ""
        nombre = ""
        idModalidad = 0
        libroCollection = arrayListOf()
    }

    override fun toString(): String {
        return "Modalidad(curso='$curso', idModalidad=$idModalidad, nombre='$nombre', libroCollection=$libroCollection)"
    }
}