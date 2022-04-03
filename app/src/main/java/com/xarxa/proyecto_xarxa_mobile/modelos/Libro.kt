package com.xarxa.proyecto_xarxa_mobile.modelos

class Libro() {

    var curso: String
    var departamento: String
    var editorial: String
    var isbnLibro: String
    var titulo: String

    constructor(
        curso: String,
        departamento: String,
        editorial: String,
        isbnLibro: String,
        titulo: String
    ) : this() {
        this.curso = curso
        this.departamento = departamento
        this.editorial = editorial
        this.isbnLibro = isbnLibro
        this.titulo = titulo
    }

    init {
        curso = ""
        departamento = ""
        editorial = ""
        isbnLibro = ""
        titulo = ""
    }

    override fun toString(): String {
        return "Libro(curso='$curso', departamento='$departamento', editorial='$editorial', isbnLibro='$isbnLibro', titulo='$titulo')"
    }
}