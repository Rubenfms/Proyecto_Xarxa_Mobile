package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoEntregaRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosEntregaBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListadoAlumnosEntregaFragment : Fragment() {

    private lateinit var _binding: LayoutListaAlumnosEntregaBinding
    private val binding get() = _binding
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: ListadoEntregaRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var añadirModificarDialog: AñadirModificarLoteFragment
    private lateinit var grupo: String
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutListaAlumnosEntregaBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerAlumnosEntrega
        adaptadorAPIRest = APIRestAdapter()
        añadirModificarDialog = AñadirModificarLoteFragment()
        recibirGrupo()
        getAlumnos()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosByGrupoAsync(grupo).await()
            cargarRecyclerAlumnos()
        }
    }

    private fun cargarRecyclerAlumnos() {
        adaptador = ListadoEntregaRecyclerAdapter(listaAlumnos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.clickListener {
            val posicion = recyclerView.getChildAdapterPosition(it)
            showPopup(recyclerView[posicion].findViewById(R.id.loteEntregadoTextView), posicion)
        }
    }

    private fun showPopup(view: View, posicion: Int) {
        val menuEmergente = PopupMenu(requireActivity(), view)
        menuEmergente.inflate(R.menu.entrega_lote_menu)
        menuEmergente.menu.setGroupVisible(
            R.id.opcionesLoteExistente,
            listaAlumnos[posicion].loteCollection.isNotEmpty()
        )
        menuEmergente.menu.setGroupVisible(
            R.id.opcionesLoteNuevo,
            listaAlumnos[posicion].loteCollection.isEmpty()
        )

        menuEmergente.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.verLoteOption -> {
                    mostrarDialogoPersonalizado(posicion)
                }
                R.id.añadirLoteOption -> {
                    navegarAñadirModificarFramgent(posicion)
                }
                R.id.modificarLoteOption -> {
                    navegarAñadirModificarFramgent(posicion)
                }
                R.id.cancelarOption -> menuEmergente.dismiss()
            }
            true
        }
        menuEmergente.show()
    }

    private fun navegarAñadirModificarFramgent(posicion: Int) {
        xarxaViewModel.setNia(listaAlumnos[posicion].nia)
        if (navController.currentDestination?.id == R.id.listadoAlumnosEntregaFragment)
            navController.navigate(R.id.action_listadoAlumnosEntregaFragment_to_añadirModificarLoteFragment)
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val idLote = listaAlumnos[posicion].loteCollection[0].idLote
        builder.setMessage(
            "El número de lote de ${listaAlumnos[posicion].nombre} ${listaAlumnos[posicion].nombre} es el Nº" +
                    " $idLote ¿Deseas verlo con más detalle?"
        )
            .setPositiveButton("Ver") { _, _ ->
                xarxaViewModel.setIdLote(idLote)
                if (navController.currentDestination?.id == R.id.listadoAlumnosEntregaFragment)
                    navController.navigate(R.id.action_listadoAlumnosEntregaFragment_to_informacionLoteFragment)
            }
            .setNegativeButton("Cerrar") { _, _ ->
            }.show()
    }

    private fun recibirGrupo() {
        val grupoObserver = Observer<String> { i -> grupo = i }
        xarxaViewModel.getGrupo().observe(requireActivity(), grupoObserver)
    }

}