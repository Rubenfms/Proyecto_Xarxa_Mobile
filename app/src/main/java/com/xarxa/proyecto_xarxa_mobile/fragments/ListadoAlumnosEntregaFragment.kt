package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoEntregaRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosEntregaBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.FiltracionService
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListadoAlumnosEntregaFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var _binding: LayoutListaAlumnosEntregaBinding
    private val binding get() = _binding
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: ListadoEntregaRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var anyadirModificarDialog: AñadirModificarLoteFragment
    private lateinit var grupo: String
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private lateinit var editTextBusqueda: EditText
    private lateinit var filtracionService: FiltracionService
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutListaAlumnosEntregaBinding.inflate(inflater, container, false)
        val view = binding.root

        setHasOptionsMenu(true)
        filtracionService = FiltracionService()
        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerAlumnosEntrega
        adaptadorAPIRest = APIRestAdapter()
        anyadirModificarDialog = AñadirModificarLoteFragment()
        recibirGrupo()
        getAlumnos()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosByGrupoAsync(grupo).await()
            cargarRecyclerAlumnos(listaAlumnos)
        }
    }

    private fun cargarRecyclerAlumnos(listaAlumnos: ArrayList<Alumno>) {
        adaptador = ListadoEntregaRecyclerAdapter(listaAlumnos)
        recyclerView.adapter = adaptador
        if (activity != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adaptador.clickListener {
                val posicion = recyclerView.getChildAdapterPosition(it)
                showPopup(recyclerView[posicion].findViewById(R.id.loteEntregadoTextView), posicion)
            }
        }
    }

    private fun showPopup(view: View, posicion: Int) {
        val menuEmergente = PopupMenu(requireActivity(), view)
        menuEmergente.inflate(R.menu.entrega_lote_menu)
        menuEmergente.menu.setGroupVisible(
            R.id.opcionesLoteExistente,
            listaAlumnos[posicion].idLote != null
        )
        menuEmergente.menu.setGroupVisible(
            R.id.opcionesLoteNuevo,
            listaAlumnos[posicion].idLote == null
        )

        menuEmergente.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.verLoteOption -> {
                    mostrarDialogoPersonalizado(posicion)
                }
                R.id.añadirLoteOption -> {
                    navegarAnyadirModificarFramgent(posicion)
                }
                R.id.modificarLoteOption -> {
                    navegarAnyadirModificarFramgent(posicion)
                }
                R.id.cancelarOption -> menuEmergente.dismiss()
            }
            true
        }
        menuEmergente.show()
    }

    private fun navegarAnyadirModificarFramgent(posicion: Int) {
        xarxaViewModel.setNia(listaAlumnos[posicion].nia)
        if (navController.currentDestination?.id == R.id.listadoAlumnosEntregaFragment)
            navController.navigate(R.id.action_listadoAlumnosEntregaFragment_to_añadirModificarLoteFragment)
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val idLote = listaAlumnos[posicion].idLote
        builder.setMessage(
            "El número de lote de ${listaAlumnos[posicion].nombre} ${listaAlumnos[posicion].nombre} es el Nº" +
                    " $idLote ¿Deseas verlo con más detalle?"
        )
            .setPositiveButton("Ver") { _, _ ->
                xarxaViewModel.setIdLote(idLote!!)
                if (navController.currentDestination?.id == R.id.listadoAlumnosEntregaFragment)
                    navController.navigate(R.id.action_listadoAlumnosEntregaFragment_to_informacionLoteFragment)
            }
            .setNegativeButton("Cerrar") { _, _ ->
            }.show()
    }

    private fun recibirGrupo() {
        val grupoObserver = Observer<String> { i -> grupo = i }
        xarxaViewModel.getGrupo().observe(requireActivity(), grupoObserver)
        binding.grupoActualEntregaTextView.text = grupo
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchview_menu, menu)
        menu.setGroupVisible(R.id.escanerGroup, false)
        val searchItem = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchItem.setOnQueryTextListener(this)
        editTextBusqueda = searchItem.findViewById(androidx.appcompat.R.id.search_src_text)
        editTextBusqueda.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.primaryTextColor
            )
        )
        editTextBusqueda.setHintTextColor(
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
        val listaFiltrada = filtracionService.filtrarPorNia(textoAFiltrar, listaAlumnos)
        cargarRecyclerAlumnos(listaFiltrada)
    }


}