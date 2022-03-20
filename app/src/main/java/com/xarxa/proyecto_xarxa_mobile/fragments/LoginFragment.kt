package com.xarxa.proyecto_xarxa_mobile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xarxa.proyecto_xarxa_mobile.activities.AccionesActivity
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutLoginBinding

class LoginFragment : Fragment() {

    private lateinit var _binding: LayoutLoginBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = LayoutLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.iniciarSesionButton.setOnClickListener {
            val intento = Intent(requireActivity(), AccionesActivity::class.java)
            startActivity(intento)
        }
        return view
    }
}