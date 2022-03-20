package com.xarxa.proyecto_xarxa_mobile.modelos

import java.util.*

class Alumno() {

    private var nia: Int
    private var nombre: String
    private var apellido1: String
    private var apellido2: String
    private var fechaNacimiento: Date
    private var estadoMatriculacion: String
    private var curso: String
    private var grupo: String
    private var incidencias: String
    private var perteneceXarxa: Boolean

    constructor(
        nia: Int,
        nombre: String,
        apellido1: String,
        apellido2: String,
        fechaNacimiento: Date,
        estadoMatriculacion: String,
        curso: String,
        grupo: String,
        incidencias: String,
        perteneceXarxa: Boolean
    ) : this() {
        this.nia = nia
        this.nombre = nombre
        this.apellido1 = apellido1
        this.apellido2 = apellido2
        this.fechaNacimiento = fechaNacimiento
        this.estadoMatriculacion = estadoMatriculacion
        this.curso = curso
        this.grupo = grupo
        this.incidencias = incidencias
        this.perteneceXarxa = perteneceXarxa
    }

    init {
        nia = 0
        nombre = ""
        apellido1 = ""
        apellido2 = ""
        fechaNacimiento = Date()
        estadoMatriculacion = ""
        curso = ""
        grupo = ""
        incidencias = ""
        perteneceXarxa = false
    }


    override fun toString(): String {
        return "Alumno(nia=$nia, nombre='$nombre', apellido1='$apellido1', apellido2='$apellido2', fechaNacimiento=$fechaNacimiento, estadoMatriculacion='$estadoMatriculacion', curso='$curso', grupo='$grupo', incidencias='$incidencias', perteneceXarxa=$perteneceXarxa)"
    }


}