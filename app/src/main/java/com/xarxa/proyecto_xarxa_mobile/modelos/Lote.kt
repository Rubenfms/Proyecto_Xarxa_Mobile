package com.xarxa.proyecto_xarxa_mobile.modelos

import java.util.*

class Lote() {

    var idLote: Int
    var idModalidad: Int
    var xarxaCollection: ArrayList<Xarxa>

    constructor(idLote: Int, idModalidad: Int, xarxaCollection: ArrayList<Xarxa>) : this() {
        this.idLote = idLote
        this.idModalidad = idModalidad
        this.xarxaCollection = xarxaCollection
    }

    init {
        idLote = 0
        idModalidad = 0
        xarxaCollection = arrayListOf()
    }

    override fun toString(): String {
        return "Lote(idLote=$idLote, idModalidad=$idModalidad, xarxaCollection=$xarxaCollection)"
    }
}