package com.xarxa.proyecto_xarxa_mobile.services

import android.util.Log
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Modalidad
import com.xarxa.proyecto_xarxa_mobile.modelos.Usuario
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
        //val url = "http://10.0.2.2:8081/apixarxa/xarxa/"
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

    fun getAlumnosByGrupoAsync(grupo: String): Deferred<ArrayList<Alumno>> {
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

    fun updateAlumnoAsync(alumno : Alumno): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.updateAlumno(alumno)
        }
    }

    fun getLotesAsync(): Deferred<ArrayList<Lote>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Lote>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Lote>> = proveedorServicios.getLotes()
            if (response.isSuccessful) {
                val lotesResponse = response.body()
                if (lotesResponse != null) {
                    respuesta = lotesResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun getLotesByNiaAsync(nia: Int): Deferred<ArrayList<Lote>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Lote>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Lote>> = proveedorServicios.getLotesByNia(nia)
            if (response.isSuccessful) {
                val lotesResponse = response.body()
                if (lotesResponse != null) {
                    respuesta = lotesResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun getLoteByIdAsync(id: Int): Deferred<Lote> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Lote()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Lote> = proveedorServicios.getLote(id)
            if (response.isSuccessful) {
                val loteResponse = response.body()
                if (loteResponse != null) {
                    respuesta = loteResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun updateLoteAsync(lote: Lote): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.updateLote(lote)
        }
    }

    fun getModalidadByIdAsync(id: Int): Deferred<Modalidad> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Modalidad()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Modalidad> = proveedorServicios.getModalidad(id)
            if (response.isSuccessful) {
                val modalidadResponse = response.body()
                if (modalidadResponse != null) {
                    respuesta = modalidadResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }

    fun getUsuarioByNombreAsync(nombre: String): Deferred<Usuario> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Usuario()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Usuario> = proveedorServicios.getUsuario(nombre)
            if (response.isSuccessful) {
                val usuarioResponse = response.body()
                if (usuarioResponse != null) {
                    respuesta = usuarioResponse
                }
            } else {
                Log.e("Error", response.errorBody().toString())
            }
            respuesta
        }
    }


}