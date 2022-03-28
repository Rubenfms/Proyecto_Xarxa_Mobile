package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutAnadirModificarLoteBinding

class AñadirModificarLoteDialogFragment : DialogFragment() {

    private var datos: ArrayList<String> = ArrayList()
    private lateinit var _binding: LayoutAnadirModificarLoteBinding
    private val binding get() = _binding
    private lateinit var navController: NavController

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        _binding = LayoutAnadirModificarLoteBinding.inflate(requireActivity().layoutInflater)
        val view = binding.root
        val builder = MaterialAlertDialogBuilder(requireActivity())
        builder.setView(view)

        navController = NavHostFragment.findNavController(this)
        datos = rellenarDatos()
        crearSpinner()

        builder.setPositiveButton("Aceptar") { _, _ ->

        }

        return builder.create()
    }

    private fun crearSpinner() {
        val spinner = binding.spinnerLotes
        spinner.adapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, datos)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pulsado: Int, p3: Long) {
                Toast.makeText(
                    requireActivity(),
                    "Has seleccionado el lote $pulsado",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun rellenarDatos(): ArrayList<String> {
        var datos: ArrayList<String> = ArrayList()
        datos.add("Lote 0")
        datos.add("Lote 1")
        datos.add("Lote 2")
        datos.add("Lote 3")
        datos.add("Lote 4")
        return datos
    }
}