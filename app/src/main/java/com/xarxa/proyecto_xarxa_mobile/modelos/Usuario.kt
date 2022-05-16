package com.xarxa.proyecto_xarxa_mobile.modelos

class Usuario() {

    var nombreUsuario: String
    var contrasenya: String
    var tipoUsuario: String

    constructor(nombreUsuario: String, contrasenya: String, tipoUsuario: String) : this() {
        this.nombreUsuario = nombreUsuario
        this.contrasenya = contrasenya
        this.tipoUsuario = tipoUsuario
    }

    init {
        nombreUsuario = ""
        contrasenya = ""
        tipoUsuario = ""
    }


}