package com.xarxa.proyecto_xarxa_mobile.modelos

import java.util.*

class Lote() {

    var idLote: Int
    var idModalidad: Int
    var niaAlumno: Int?
    var xarxaCollection: ArrayList<Xarxa>
    var nombreModalidad : String

    constructor(
        idLote: Int,
        idModalidad: Int,
        niaAlumno: Int,
        nombreModalidad: String,
        xarxaCollection: ArrayList<Xarxa>
    ) : this() {
        this.idLote = idLote
        this.idModalidad = idModalidad
        this.niaAlumno = niaAlumno
        this.nombreModalidad = nombreModalidad
        this.xarxaCollection = xarxaCollection
    }

    init {
        niaAlumno = null
        idLote = 0
        idModalidad = 0
        nombreModalidad = ""
        xarxaCollection = arrayListOf()
    }

    override fun toString(): String {
        return "Lote(idLote=$idLote, idModalidad=$idModalidad, xarxaCollection=$xarxaCollection)"
    }
}