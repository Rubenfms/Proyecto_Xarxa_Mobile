package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutInformacionAlumnoBinding

class InformacionAlumnoFragment : Fragment() {

    private lateinit var _binding: LayoutInformacionAlumnoBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutInformacionAlumnoBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }
}