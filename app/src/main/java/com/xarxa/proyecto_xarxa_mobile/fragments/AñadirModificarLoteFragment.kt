package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutAnadirModificarLoteBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Modalidad
import com.xarxa.proyecto_xarxa_mobile.recyclers.LotesEntregaRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AñadirModificarLoteFragment : Fragment(), SearchView.OnQueryTextListener {

    private var listaLotes: ArrayList<Lote> = ArrayList()
    private lateinit var _binding: LayoutAnadirModificarLoteBinding
    private val binding get() = _binding
    private var nia: Int = 0
    private lateinit var adaptador: LotesEntregaRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

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
        adaptadorAPIRest = APIRestAdapter()
        recibirNIA()
        getLotes()

        return view
    }

    private fun getLotes() {
        CoroutineScope(Dispatchers.Main).launch {
            listaLotes = adaptadorAPIRest.getLotesByNiaAsync(0).await()
            for (lote in listaLotes) {
                val modalidad: Modalidad =
                    adaptadorAPIRest.getModalidadByIdAsync(lote.idModalidad).await()
                lote.nombreModalidad = modalidad.nombre
            }
            cargarRecyclerLotes()
        }
    }

    private fun cargarRecyclerLotes() {
        adaptador = LotesEntregaRecyclerAdapter(listaLotes)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.clickListener {
            val posicion = recyclerView.getChildAdapterPosition(it)
            mostrarDialogoPersonalizado(posicion)
        }
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
            cargarRecyclerLotes()
        } else {
            val listaFiltrada = ArrayList<Lote>()
            for (x in listaLotes) {
                val text = x.idLote
                if (text == textoAFiltrar.toInt()) listaFiltrada.add(x)
                else if (text.toString().indexOf(textoAFiltrar.lowercase()) == 0) listaFiltrada.add(
                    x
                )
                adaptador = LotesEntregaRecyclerAdapter(listaFiltrada)
                recyclerView.adapter = adaptador
            }
        }
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val nombreModalidad = listaLotes[posicion].nombreModalidad
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(
            "¿Deseas ver con más detalle el lote del curso $nombreModalidad?"
        )
            .setPositiveButton("Ver") { _, _ ->
                xarxaViewModel.setIdLote(listaLotes[posicion].idLote)
                if (navController.currentDestination?.id == R.id.añadirModificarLoteFragment)
                    navController.navigate(R.id.action_añadirModificarLoteFragment_to_informacionLoteFragment)
            }
            .setNegativeButton("Cerrar") { _, _ ->
            }.show()
    }

    private fun recibirNIA() {
        val niaObserver = Observer<Int> { i -> nia = i }
        xarxaViewModel.getNia().observe(requireActivity(), niaObserver)

        if (nia == 0) binding.eligeLoteTextView.text =
            "Añadir lote" else binding.eligeLoteTextView.text = "Modificar lote"
    }

}