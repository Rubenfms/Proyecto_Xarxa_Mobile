package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutAnadirModificarLoteBinding
import com.xarxa.proyecto_xarxa_mobile.recyclers.LotesEntregaRecyclerAdapter

class AñadirModificarLoteFragment : Fragment(), SearchView.OnQueryTextListener {

    private var datos: ArrayList<String> = ArrayList()
    private lateinit var _binding: LayoutAnadirModificarLoteBinding
    private val binding get() = _binding
    private lateinit var adaptador: LotesEntregaRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutAnadirModificarLoteBinding.inflate(requireActivity().layoutInflater)
        val view = binding.root

        setHasOptionsMenu(true)
        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerLotesEntrega
        datos = rellenarDatos()
        cargarRecyclerLotes()

        return view
    }

    private fun cargarRecyclerLotes() {
        adaptador = LotesEntregaRecyclerAdapter(datos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchview_menu, menu)
        menu.setGroupVisible(R.id.escanerGroup, false)
        val searchItem = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchItem.setOnQueryTextListener(this)
        val editText = searchItem.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.primaryTextColor))
        editText.setHintTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.primaryTextColor
            )
        )
    }

    override fun onQueryTextSubmit(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
    }

    override fun onQueryTextChange(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
    }

    private fun filtrar(textoAFiltrar: String) {
        if (TextUtils.isEmpty(textoAFiltrar)) {
            adaptador = LotesEntregaRecyclerAdapter(datos)
            recyclerView.adapter = adaptador
        } else {
            val listaFiltrada = ArrayList<String>()
            for (x in datos) {
                val text = x.lowercase()
                if (text.contains(textoAFiltrar.lowercase())) listaFiltrada.add(x)
                else if (text.indexOf(textoAFiltrar.lowercase()) == 0) listaFiltrada.add(x)
                adaptador = LotesEntregaRecyclerAdapter(listaFiltrada)
                recyclerView.adapter = adaptador
            }
        }
    }
}