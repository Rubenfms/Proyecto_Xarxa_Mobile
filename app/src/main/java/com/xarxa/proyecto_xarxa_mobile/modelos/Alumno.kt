package com.xarxa.proyecto_xarxa_mobile.modelos

import java.util.*

class Alumno() {

    var nia: Int
    var nombre: String
    var apellido1: String
    var apellido2: String
    var estadoMatriculacion: String
    var curso: String
    var grupo: String
    var incidencias: String? = null
    var perteneceXarxa: Boolean
    var loteCollection: ArrayList<Lote>
    var estadoLote: String

    constructor(
        nia: Int,
        nombre: String,
        apellido1: String,
        apellido2: String,
        estadoMatriculacion: String,
        curso: String,
        grupo: String,
        incidencias: String,
        perteneceXarxa: Boolean,
        loteCollection: ArrayList<Lote>,
        estadoLote : String
    ) : this() {
        this.nia = nia
        this.nombre = nombre
        this.apellido1 = apellido1
        this.apellido2 = apellido2
        this.estadoMatriculacion = estadoMatriculacion
        this.curso = curso
        this.grupo = grupo
        this.incidencias = incidencias
        this.perteneceXarxa = perteneceXarxa
        this.loteCollection = loteCollection
        this.estadoLote = estadoLote
    }

    init {
        nia = 0
        nombre = ""
        apellido1 = ""
        apellido2 = ""
        estadoMatriculacion = ""
        curso = ""
        grupo = ""
        estadoLote = ""
        perteneceXarxa = false
        loteCollection = arrayListOf()
    }

    override fun toString(): String {
        return "Alumno(nia=$nia, nombre='$nombre', apellido1='$apellido1', apellido2='$apellido2', estadoMatriculacion='$estadoMatriculacion', curso='$curso', grupo='$grupo', incidencias='$incidencias', perteneceXarxa=$perteneceXarxa, loteCollection=$loteCollection, estadoLote='$estadoLote')"
    }


}