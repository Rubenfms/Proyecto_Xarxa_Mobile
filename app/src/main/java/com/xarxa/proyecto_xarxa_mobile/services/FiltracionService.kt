package com.xarxa.proyecto_xarxa_mobile.services

import android.text.TextUtils
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class FiltracionService {

    fun filtrarPorNia(textoAFiltrar: String, listaAlumnos: ArrayList<Alumno>): ArrayList<Alumno> {
        val textoFiltrarMinus = textoAFiltrar.lowercase()
        if (TextUtils.isEmpty(textoAFiltrar)) {
            return listaAlumnos
        } else {
            val listaFiltrada = ArrayList<Alumno>()
            for (x in listaAlumnos) {
                val niaAlumno = x.nia.toString().lowercase()
                if (niaAlumno.contains(textoFiltrarMinus)) {
                    listaFiltrada.add(x)
                } else if (niaAlumno.indexOf(textoFiltrarMinus) == 0) {
                    listaFiltrada.add(x)
                }
            }
            return listaFiltrada
        }
    }

    fun filtrarPorNombre(
        textoAFiltrar: String,
        listaAlumnos: ArrayList<Alumno>
    ): ArrayList<Alumno> {
        val textoFiltrarMinus = textoAFiltrar.lowercase()
        if (TextUtils.isEmpty(textoAFiltrar)) {
            return listaAlumnos
        } else {
            val listaFiltrada = ArrayList<Alumno>()
            for (x in listaAlumnos) {
                val nombreAlumno = x.nombre.lowercase() + x.apellido1.lowercase()
                if (nombreAlumno.contains(textoFiltrarMinus)) {
                    listaFiltrada.add(x)
                } else if (nombreAlumno.indexOf(textoFiltrarMinus) == 0) {
                    listaFiltrada.add(x)
                }
            }
            return listaFiltrada
        }
    }

    fun filtrarPorNumeroLote(
        textoAFiltrar: String,
        listaAlumnos: ArrayList<Alumno>
    ): ArrayList<Alumno> {
        val textoFiltrarMinus = textoAFiltrar.lowercase()
        if (TextUtils.isEmpty(textoAFiltrar)) {
            return listaAlumnos
        } else {
            val listaFiltrada = ArrayList<Alumno>()
            for (x in listaAlumnos) {
                if (x.idLote != null) {
                    val lote = x.idLote.toString().lowercase()
                    if (lote.contains(textoFiltrarMinus)) {
                        listaFiltrada.add(x)
                    } else if (lote.indexOf(textoFiltrarMinus) == 0) {
                        listaFiltrada.add(x)
                    }
                }
            }
            return listaFiltrada
        }
    }
}