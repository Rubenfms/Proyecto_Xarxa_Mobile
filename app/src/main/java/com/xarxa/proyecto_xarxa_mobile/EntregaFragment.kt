package com.xarxa.proyecto_xarxa_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutEntregaBinding

class EntregaFragment : Fragment() {

    private lateinit var _binding: LayoutEntregaBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutEntregaBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.entregaOption).isChecked = true

        return view
    }
}