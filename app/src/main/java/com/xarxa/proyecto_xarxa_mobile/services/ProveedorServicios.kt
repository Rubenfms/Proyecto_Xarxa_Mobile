package com.xarxa.proyecto_xarxa_mobile.services

import com.xarxa.proyecto_xarxa_mobile.modelos.*
import retrofit2.Response
import retrofit2.http.*

interface ProveedorServicios {

    @GET("alumnos")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumnos(): Response<ArrayList<Alumno>>

    @GET("alumnos/{nia}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumno(@Path("nia") nia: Int): Response<Alumno>

    @GET("alumnos/grupo/{grupo}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getAlumnosByGrupo(@Path("grupo") grupo: String): Response<ArrayList<Alumno>>

    @GET("lotes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLotes(): Response<ArrayList<Lote>>

    @GET("lotes/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLote(@Path("id") id: Int): Response<Lote>

    @GET("lotes/nia/{nia}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLotesByNia(@Path("nia") nia: Int): Response<ArrayList<Lote>>

    @PUT("lotes")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun updateLote(@Body lote: Lote): Response<Void>

    @GET("modalidades/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getModalidad(@Path("id") id: Int): Response<Modalidad>

    @GET("libros")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibros(): Response<ArrayList<Libro>>

    @GET("libros/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibro(@Path("id") id: Int): Response<Libro>

    @GET("usuarios/{nombre}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getUsuario(@Path("nombre") nombre: String): Response<Usuario>


}