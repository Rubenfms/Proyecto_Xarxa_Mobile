package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoDevolucionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosDevolucionBinding

class ListadoAlumnosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutListaAlumnosDevolucionBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: ListadoDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutListaAlumnosDevolucionBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerAlumnosDevolucion
        datos = rellenarDatos()
        cargarRecyclerCursos()

        return view
    }

    private fun cargarRecyclerCursos() {
        adaptador = ListadoDevolucionRecyclerAdapter(datos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun rellenarDatos(): ArrayList<String> {
        var datos: ArrayList<String> = ArrayList()
        datos.add("Juandi Cabrera Soler")
        datos.add("Rubén Sánchez")
        datos.add("Joaquín Cutillas")
        return datos
    }
}