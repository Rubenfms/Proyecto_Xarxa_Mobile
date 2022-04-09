package com.xarxa.proyecto_xarxa_mobile.services

import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.modelos.Libro
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Modalidad
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

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

    @GET("modalidades/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getModalidad(@Path("id") id: Int): Response<Modalidad>

    @GET("libros")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibros(): Response<ArrayList<Libro>>

    @GET("libros/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibro(@Path("id") id: Int): Response<Libro>


}