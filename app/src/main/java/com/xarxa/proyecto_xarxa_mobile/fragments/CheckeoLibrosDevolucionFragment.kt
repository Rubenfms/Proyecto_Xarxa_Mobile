package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutCheckeoIncidenciasDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.recyclers.CursosRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.recyclers.LibrosDevolucionRecyclerAdapter

class CheckeoLibrosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutCheckeoIncidenciasDevolucionBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: LibrosDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutCheckeoIncidenciasDevolucionBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerLibrosDevolucion
        datos = rellenarDatos()
        cargarRecyclerCursos()

        binding.aceptarButton.setOnClickListener {
            mostrarDialogoPersonalizado()
        }

        return view
    }

    private fun cargarRecyclerCursos() {
        adaptador = LibrosDevolucionRecyclerAdapter(datos)
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
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        datos.add("Matemáticas")
        return datos
    }

    private fun mostrarDialogoPersonalizado(
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setMessage(
            "¿Datos correctos?"
        )
            .setPositiveButton("Aceptar") { _, _ ->
                if (navController.currentDestination?.id == R.id.checkeoLibrosDevolucionFragment)
                    navController.navigate(R.id.action_global_listadoAlumnosDevolucionFragment)
            }
            .setNegativeButton("CANCELAR")
            { _, _ -> }
            .show()
    }
}