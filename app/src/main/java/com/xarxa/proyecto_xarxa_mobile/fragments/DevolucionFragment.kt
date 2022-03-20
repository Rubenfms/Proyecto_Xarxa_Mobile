package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.recyclers.CursosRecyclerAdapter

class DevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutDevolucionBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: CursosRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutDevolucionBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.devolucionOption).isChecked =
            true

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerCursosDevolucion
        datos = rellenarDatos()
        cargarRecyclerCursos()

        adaptador.onClickListenerCursos {
            if (navController.currentDestination?.id == R.id.devolucionFragment)
                navController.navigate(R.id.action_devolucionFragment_to_listadoAlumnosDevolucionFragment)
        }

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