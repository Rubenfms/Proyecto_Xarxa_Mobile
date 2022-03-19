package com.xarxa.proyecto_xarxa_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosEntregaBinding

class ListadoAlumnosEntregaFragment : Fragment() {

    private lateinit var _binding: LayoutListaAlumnosEntregaBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: ListadoEntregaRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutListaAlumnosEntregaBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerAlumnosEntrega
        datos = rellenarDatos()
        cargarRecyclerCursos()

        return view
    }

    private fun cargarRecyclerCursos() {
        adaptador = ListadoEntregaRecyclerAdapter(datos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun rellenarDatos(): ArrayList<String> {
        var datos: ArrayList<String> = ArrayList()
        datos.add("Juandi Cabrera")
        datos.add("Rubén Sánchez")
        datos.add("Joaquín Cutillas")
        return datos
    }
}