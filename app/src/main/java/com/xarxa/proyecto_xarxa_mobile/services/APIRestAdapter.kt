package com.xarxa.proyecto_xarxa_mobile.services

import android.util.Log
import android.widget.Toast
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Modalidad
import com.xarxa.proyecto_xarxa_mobile.modelos.Usuario
import kotlinx.coroutines.*
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIRestAdapter {

    private fun inicializarRetrofit(): ProveedorServicios {

        val url = "http://192.168.0.12:8081/apixarxa/xarxa/"
        //val url = "http://10.0.2.2:8081/apixarxa/xarxa/"
        //val url = "http://xarxa.eastus.cloudapp.azure.com:8081/apixarxa/xarxa/"

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProveedorServicios::class.java)
    }

    fun getAlumnosAsync(sessionId: String): Deferred<ArrayList<Alumno>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Alumno>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Alumno>> = proveedorServicios.getAlumnos(sessionId)
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

    fun getAlumnoByNiaAsync(nia: Int, sessionId: String): Deferred<Alumno> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Alumno()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Alumno> = proveedorServicios.getAlumno(nia, sessionId)
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

    fun getAlumnosByCursoAsync(curso: String, sessionId: String): Deferred<ArrayList<Alumno>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Alumno>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Alumno>> = proveedorServicios.getAlumnosByCurso(curso, sessionId)
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

    fun getAlumnosByGrupoAsync(grupo: String, sessionId: String): Deferred<ArrayList<Alumno>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Alumno>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Alumno>> = proveedorServicios.getAlumnosByGrupo(grupo, sessionId)
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

    fun updateAlumnoAsync(alumno: Alumno, sessionId: String): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.updateAlumno(alumno, sessionId)
        }
    }

    fun getLotesAsync(sessionId: String): Deferred<ArrayList<Lote>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Lote>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Lote>> = proveedorServicios.getLotes(sessionId)
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

    fun getLotesByNiaAsync(nia: Int, sessionId: String): Deferred<ArrayList<Lote>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = ArrayList<Lote>()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<ArrayList<Lote>> = proveedorServicios.getLotesByNia(nia, sessionId)
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

    fun getLoteByIdAsync(id: Int, sessionId: String): Deferred<Lote> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Lote()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Lote> = proveedorServicios.getLote(id, sessionId)
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

    fun updateLoteAsync(lote: Lote, sessionId: String): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.updateLote(lote, sessionId)
        }
    }

    fun getModalidadByIdAsync(id: Int, sessionId: String): Deferred<Modalidad> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Modalidad()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Modalidad> = proveedorServicios.getModalidad(id, sessionId)
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

    fun getUsuarioByNombreAsync(nombre: String, sessionId: String): Deferred<Usuario> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        var respuesta = Usuario()
        return CoroutineScope(Dispatchers.Main).async {
            val response: Response<Usuario> = proveedorServicios.getUsuario(nombre, sessionId)
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

    fun loginUsuarioAsync(usuario: Usuario,): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.loginUsuario(usuario)
        }
    }

    fun logoutUsuarioAsync(): Deferred<Response<Void>> {
        val proveedorServicios: ProveedorServicios = inicializarRetrofit()
        return CoroutineScope(Dispatchers.IO).async {
            proveedorServicios.logout()
        }
    }



}