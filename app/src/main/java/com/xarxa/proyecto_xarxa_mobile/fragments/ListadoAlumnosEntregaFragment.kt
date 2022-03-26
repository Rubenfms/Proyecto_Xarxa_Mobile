package com.xarxa.proyecto_xarxa_mobile.fragments

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoEntregaRecyclerAdapter
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
        cargarRecycler()

        adaptador.longClick {
            val posicion = recyclerView.getChildAdapterPosition(it)
            showPopup(recyclerView[posicion].findViewById(R.id.loteEntregadoCheckBox), posicion)
            true
        }

        return view
    }

    private fun cargarRecycler() {
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

    private fun showPopup(view: View, posicion: Int) {
        val menuEmergente = PopupMenu(requireActivity(), view)
        menuEmergente.inflate(R.menu.entrega_lote_menu)
        menuEmergente.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.verLoteOption -> {
                    mostrarDialogoPersonalizado(posicion)
                }
                R.id.añadirLoteOption -> {

                }
                R.id.modificarLoteOption -> {

                }
                R.id.cancelarOption -> menuEmergente.dismiss()
            }
            true
        }
        menuEmergente.show()
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val lote = "LOTE X"
        builder.setMessage(
            "El lote de ${datos[posicion]} es $lote"
        )
            .setPositiveButton("Aceptar") { _, _ ->

            }.show()
    }

}