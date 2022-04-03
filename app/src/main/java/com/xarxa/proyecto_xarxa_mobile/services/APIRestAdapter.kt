package com.xarxa.proyecto_xarxa_mobile.services

import android.util.Log
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRestAdapter {

    private fun inicializarRetrofit(): ProveedorServicios {
        val url = "http://192.168.0.162:8081/apixarxa/xarxa/"
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProveedorServicios::class.java)
    }

    fun getAlumnosAsync(): Deferred<ArrayList<Alumno>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Alumno>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Alumno>> = proveedorServicios.getAlumnos()
            if (response.isSuccessful) {
                val alumnosResponse = response.body()
                if (alumnosResponse != null) {
                    respuesta = alumnosResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun getAlumnoByNiaAsync(nia: Int): Deferred<Alumno> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Alumno()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Alumno> = proveedorServicios.getAlumno(nia)
            if (response.isSuccessful) {
                val alumnosResponse = response.body()
                if (alumnosResponse != null) {
                    respuesta = alumnosResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun getAlumnosByGrupoAsync(grupo : String): Deferred<ArrayList<Alumno>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Alumno>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Alumno>> = proveedorServicios.getAlumnosByGrupo(grupo)
            if (response.isSuccessful) {
                val alumnosResponse = response.body()
                if (alumnosResponse != null) {
                    respuesta = alumnosResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }
}