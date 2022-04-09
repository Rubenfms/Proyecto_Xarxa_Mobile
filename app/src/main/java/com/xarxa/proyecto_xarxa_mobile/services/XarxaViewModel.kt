package com.xarxa.proyecto_xarxa_mobile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class XarxaViewModel : ViewModel() {

    private val nia = MutableLiveData<Int>()
    private val curso = MutableLiveData<String>()
    private val idLote = MutableLiveData<Int>()

    fun setIdLote(id : Int) {
        idLote.value = id
    }

    fun getIdLote() : LiveData<Int> {
        return idLote
    }

    fun setCurso(c : String) {
        curso.value = c
    }

    fun getCurso() : LiveData<String> {
        return curso
    }

    fun setNia(n: Int) {
        nia.value = n
    }

    fun getNia(): LiveData<Int> {
        return nia
    }


}