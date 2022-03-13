package com.xarxa.proyecto_xarxa_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutBusquedaLibrosBinding

class BusquedaLibrosFragment : Fragment() {

    private lateinit var _binding: LayoutBusquedaLibrosBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutBusquedaLibrosBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.busquedaLibrosOption).isChecked = true


        return view
    }
}