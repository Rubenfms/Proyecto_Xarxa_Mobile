package com.xarxa.proyecto_xarxa_mobile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class XarxaViewModel : ViewModel() {

    private val nia = MutableLiveData<Int>()
    private val curso = MutableLiveData<String>()
    private val grupo = MutableLiveData<String>()
    private val tipoUsuario = MutableLiveData<String>()
    private val idLote = MutableLiveData<Int>()
    private val accionElegida = MutableLiveData<String>()
    private val sessionId = MutableLiveData<String>()

    fun setSessionId(id : String) {
        sessionId.value = id
    }

    fun getSessionId() : LiveData<String> {
        return sessionId
    }

    fun getSessionIdString(): String {
        return sessionId.value.toString()
    }

    fun setIdLote(id: Int) {
        idLote.value = id
    }

    fun getIdLote(): LiveData<Int> {
        return idLote
    }

    fun setTipoUsuario(usuario: String) {
        tipoUsuario.value = usuario
    }

    fun getTipoUsuario(): LiveData<String> {
        return tipoUsuario
    }

    fun setAccionElegida(accion: String) {
        accionElegida.value = accion
    }

    fun getAccionElegida(): LiveData<String> {
        return accionElegida
    }

    fun setCurso(c: String) {
        curso.value = c
    }

    fun getCurso(): LiveData<String> {
        return curso
    }

    fun setGrupo(group: String) {
        grupo.value = group
    }

    fun getGrupo(): LiveData<String> {
        return grupo
    }

    fun setNia(n: Int) {
        nia.value = n
    }

    fun getNia(): LiveData<Int> {
        return nia
    }


}