package com.xarxa.proyecto_xarxa_mobile.services

import com.xarxa.proyecto_xarxa_mobile.modelos.Xarxa

interface PasarDatosInterface {
    fun pasarLibro(libro: Xarxa, checkeado: Boolean)
}