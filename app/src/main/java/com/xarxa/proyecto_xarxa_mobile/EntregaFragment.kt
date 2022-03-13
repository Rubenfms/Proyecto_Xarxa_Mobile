package com.xarxa.proyecto_xarxa_mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutEntregaBinding

class EntregaFragment : Fragment() {

    private lateinit var _binding: LayoutEntregaBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: CursosRecyclerAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutEntregaBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.entregaOption).isChecked =
            true

        recyclerView = binding.recyclerCursosEntrega
        datos = rellenarDatos()
        cargarRecyclerCursos()

        return view
    }

    private fun cargarRecyclerCursos() {
        adaptador = CursosRecyclerAdapter(datos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 3)
    }

    private fun rellenarDatos(): ArrayList<String> {
        var datos: ArrayList<String> = ArrayList()
        datos.add("1ªESO A")
        datos.add("1ºESO B")
        datos.add("1ºESO C")
        datos.add("2ºESO A")
        datos.add("3ºESO A")
        datos.add("4ºESO A")
        return datos
    }
}