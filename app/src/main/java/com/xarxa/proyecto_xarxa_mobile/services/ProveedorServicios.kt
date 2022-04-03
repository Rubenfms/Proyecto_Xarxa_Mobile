package com.xarxa.proyecto_xarxa_mobile.services

import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.modelos.Libro
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

    @GET("libros")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibros(): Response<ArrayList<Libro>>

    @GET("libros/{id}")
    @Headers("Accept: application/json", "Content-Type: application/json")
    suspend fun getLibro(@Path("id") id: Int): Response<Libro>


}