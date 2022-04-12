package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoDevolucionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutListaAlumnosDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListadoAlumnosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutListaAlumnosDevolucionBinding
    private val binding get() = _binding
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: ListadoDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var grupo: String
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()


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
        adaptadorAPIRest = APIRestAdapter()
        recibirCurso()
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
        adaptador = ListadoDevolucionRecyclerAdapter(listaAlumnos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.clickListener {
            val posicion = recyclerView.getChildAdapterPosition(it)
            if (listaAlumnos[posicion].loteCollection.isNotEmpty())
                mostrarDialogoPersonalizado(posicion)
        }
    }

    private fun recibirCurso() {
        val grupoObserver = Observer<String> { i -> grupo = i }
        xarxaViewModel.getCurso().observe(requireActivity(), grupoObserver)
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setMessage(
            "Vas a devolver el lote de ${listaAlumnos[posicion].nombre} ${listaAlumnos[posicion].apellido1} ¿Estás segur@?"
        )
            .setPositiveButton("Aceptar") { _, _ ->
                xarxaViewModel.setNia(listaAlumnos[posicion].nia)
                if (navController.currentDestination?.id == R.id.listadoAlumnosDevolucionFragment)
                    navController.navigate(R.id.action_listadoAlumnosDevolucionFragment_to_checkeoLibrosDevolucionFragment)
            }
            .setNegativeButton("CANCELAR")
            { _, _ -> }
            .show()
    }
}