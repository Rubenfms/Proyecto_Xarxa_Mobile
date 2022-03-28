package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoDevolucionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.services.PasarPosicionInterface

class ListadoAlumnosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutListaAlumnosDevolucionBinding
    private val binding get() = _binding
    private var datos: ArrayList<String> = ArrayList()
    private lateinit var adaptador: ListadoDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutListaAlumnosDevolucionBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerAlumnosDevolucion
        datos = rellenarDatos()
        cargarRecycler()

        adaptador.clickListener {
            val posicion = recyclerView.getChildAdapterPosition(it)
            mostrarDialogoPersonalizado(posicion)
            true
        }

        return view
    }

    private fun cargarRecycler() {
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

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setMessage(
            "Vas a devolver el lote de ${datos[posicion]} ¿Estás segur@?"
        )
            .setPositiveButton("Aceptar") { _, _ ->
                if (navController.currentDestination?.id == R.id.listadoAlumnosDevolucionFragment)
                    navController.navigate(R.id.action_listadoAlumnosDevolucionFragment_to_checkeoLibrosDevolucionFragment)
            }
            .setNegativeButton("CANCELAR")
            { _, _ -> }
            .show()
    }
}