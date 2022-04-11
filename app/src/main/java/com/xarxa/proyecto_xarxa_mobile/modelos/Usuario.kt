package com.xarxa.proyecto_xarxa_mobile.modelos

class Usuario() {

    var nombre: String
    var contrasenya: String
    var tipoUsuario: String

    constructor(nombre: String, contrasenya: String, tipoUsuario: String) : this() {
        this.nombre = nombre
        this.contrasenya = contrasenya
        this.tipoUsuario = tipoUsuario
    }

    init {
        nombre = ""
        contrasenya = ""
        tipoUsuario = ""
    }


}