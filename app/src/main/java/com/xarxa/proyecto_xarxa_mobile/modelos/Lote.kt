package com.xarxa.proyecto_xarxa_mobile.modelos

class Lote() {

    var idLote: Int
    var niaAlumno: Int?
    var librosLote: ArrayList<Xarxa>
    var modalidadLote: Modalidad

    constructor(
        idLote: Int,
        niaAlumno: Int?,
        librosLote: ArrayList<Xarxa>,
        modalidadLote: Modalidad
    ) : this() {
        this.idLote = idLote
        this.niaAlumno = niaAlumno
        this.librosLote = librosLote
        this.modalidadLote = modalidadLote
    }

    init {
        niaAlumno = null
        idLote = 0
        modalidadLote = Modalidad()
        librosLote = arrayListOf()
    }

    override fun toString(): String {
        return "Lote(idLote=$idLote, niaAlumno=$niaAlumno, librosLote=$librosLote, modalidadLote=$modalidadLote)"
    }


}