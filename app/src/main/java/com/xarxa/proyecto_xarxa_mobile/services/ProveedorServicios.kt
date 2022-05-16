package com.xarxa.proyecto_xarxa_mobile.services

import com.xarxa.proyecto_xarxa_mobile.modelos.*
import retrofit2.Response
import retrofit2.http.*

interface ProveedorServicios {

    @GET("alumnos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumnos(@Header("Cookie") sessionId : String): Response<ArrayList<Alumno>>

    @GET("alumnos/{nia}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumno(@Path("nia") nia: Int, @Header("Cookie") sessionId : String) : Response<Alumno>

    @GET("alumnos/curso/{curso}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumnosByCurso(@Path("curso") curso: String, @Header("Cookie") sessionId : String): Response<ArrayList<Alumno>>

    @GET("alumnos/grupo/{grupo}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumnosByGrupo(@Path("grupo") grupo: String, @Header("Cookie") sessionId : String): Response<ArrayList<Alumno>>

    @PUT("alumnos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun updateAlumno(@Body alumno: Alumno, @Header("Cookie") sessionId : String): Response<Void>

    @GET("lotes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLotes(@Header("Cookie") sessionId : String): Response<ArrayList<Lote>>

    @GET("lotes/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLote(@Path("id") id: Int, @Header("Cookie") sessionId : String): Response<Lote>

    @GET("lotes/nia/{nia}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLotesByNia(@Path("nia") nia: Int, @Header("Cookie") sessionId : String): Response<ArrayList<Lote>>

    @PUT("lotes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun updateLote(@Body lote: Lote, @Header("Cookie") sessionId : String): Response<Void>

    @GET("modalidades/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getModalidad(@Path("id") id: Int, @Header("Cookie") sessionId : String): Response<Modalidad>

    @GET("libros")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibros(): Response<ArrayList<Libro>>

    @GET("libros/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibro(@Path("id") id: Int): Response<Libro>

    @GET("usuarios/{nombre}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getUsuario(@Path("nombre") nombre: String, @Header("Cookie") sessionId : String): Response<Usuario>

    @POST("auth/login")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun loginUsuario(@Body usuario: Usuario): Response<Void>

    @POST("auth/logout")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun logout(): Response<Void>

}