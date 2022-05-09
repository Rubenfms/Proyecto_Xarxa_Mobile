package com.xarxa.proyecto_xarxa_mobile.modelos

class Xarxa() {

    var codigoXarxa: Int
    var isbn: String
    var departamento: String
    var curso: String
    var titulo: String
    var editorial: String

    constructor(
        codigoXarxa: Int,
        isbn: String,
        departamento: String,
        curso: String,
        titulo: String,
        editorial: String
    ) : this() {
        this.codigoXarxa = codigoXarxa
        this.isbn = isbn
        this.departamento = departamento
        this.curso = curso
        this.titulo = titulo
        this.editorial = editorial
    }

    init {
        codigoXarxa = 0
        curso = ""
        departamento = ""
        editorial = ""
        isbn = ""
        titulo = ""
    }

    override fun toString(): String {
        return "Xarxa(codigoXarxa=$codigoXarxa, isbn=$isbn, departamento=$departamento, curso=$curso, titulo='$titulo', editorial=$editorial)"
    }
}