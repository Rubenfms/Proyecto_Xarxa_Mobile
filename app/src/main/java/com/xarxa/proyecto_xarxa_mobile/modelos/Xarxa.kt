package com.xarxa.proyecto_xarxa_mobile.modelos

class Xarxa() {

    var codigoXarxa: Int
    var libro: Libro

    constructor(codigoXarxa: Int, libro: Libro) : this() {
        this.codigoXarxa = codigoXarxa
        this.libro = libro
    }

    init {
        codigoXarxa = 0
        libro = Libro()
    }

    override fun toString(): String {
        return "Xarxa(codigoXarxa=$codigoXarxa, libro=$libro)"
    }
}