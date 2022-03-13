package com.xarxa.proyecto_xarxa_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutLocalizacionBinding

class LocalizacionFragment : Fragment() {

    private lateinit var _binding: LayoutLocalizacionBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutLocalizacionBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.localizacionOption).isChecked = true


        return view
    }
}