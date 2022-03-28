package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutInformacionLoteBinding
import com.xarxa.proyecto_xarxa_mobile.recyclers.LibrosInformacionLoteRecyclerAdapter

class InformacionLoteFragment : Fragment() {

    private lateinit var _binding: LayoutInformacionLoteBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: LibrosInformacionLoteRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutInformacionLoteBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerLibrosLote
        datos = rellenarDatos()
        cargarRecycler()

        return view
    }

    private fun cargarRecycler() {
        adaptador = LibrosInformacionLoteRecyclerAdapter(datos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun rellenarDatos(): ArrayList<String> {
        var datos: ArrayList<String> = ArrayList()
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        return datos
    }

}