package com.xarxa.proyecto_xarxa_mobile.modelos

class Xarxa() {

    var codigoXarxa: Int
    var isbn: Libro

    constructor(codigoXarxa: Int, libro: Libro) : this() {
        this.codigoXarxa = codigoXarxa
        this.isbn = libro
    }

    init {
        codigoXarxa = 0
        isbn = Libro()
    }

    override fun toString(): String {
        return "Xarxa(codigoXarxa=$codigoXarxa, libro=$isbn)"
    }
}