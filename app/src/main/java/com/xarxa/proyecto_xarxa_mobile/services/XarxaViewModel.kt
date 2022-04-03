package com.xarxa.proyecto_xarxa_mobile.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class XarxaViewModel : ViewModel() {

    private val nia = MutableLiveData<Int>()
    private val curso = MutableLiveData<String>()

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