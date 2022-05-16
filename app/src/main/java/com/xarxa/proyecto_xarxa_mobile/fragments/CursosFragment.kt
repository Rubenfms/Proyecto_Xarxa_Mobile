package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.util.Log
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
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutCursosBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.recyclers.CursosGruposRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CursosFragment : Fragment() {

    private lateinit var _binding: LayoutCursosBinding
    private val binding get() = _binding
    private var listaCursos: ArrayList<String> = ArrayList()
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: CursosGruposRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var accionElegida: String
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutCursosBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerCursos
        adaptadorAPIRest = APIRestAdapter()
        recibirAccionElegida()
        getAlumnos()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosAsync(xarxaViewModel.getSessionIdString()).await()
            logicaCursos()
        }
    }

    private fun cargarRecyclerCursos() {
        adaptador = CursosGruposRecyclerAdapter(listaCursos)
        if (activity != null) {
            recyclerView.adapter = adaptador
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adaptador.onClickListener {
                val posicion = recyclerView.getChildAdapterPosition(it)
                xarxaViewModel.setCurso(listaCursos[posicion])
                if (navController.currentDestination?.id == R.id.cursosFragment)
                    navController.navigate(R.id.action_cursosFragment_to_gruposFragment)
            }
        }

    }

    private fun logicaCursos() {
        for (i in 0 until listaAlumnos.size) {
            var cursoAlumno = listaAlumnos[i].curso
            if (!listaCursos.contains(cursoAlumno)) {
                listaCursos.add(cursoAlumno)
            }
        }
        listaCursos.sort()
        cargarRecyclerCursos()
    }

    private fun recibirAccionElegida() {
        val accionObserver = Observer<String> { i -> accionElegida = i }
        xarxaViewModel.getAccionElegida().observe(requireActivity(), accionObserver)
        val accionElegidaInformacion = binding.informacionAccionCursosTextView
        when (xarxaViewModel.getAccionElegida().value) {
            getString(R.string.entrega) -> {
                accionElegidaInformacion.text = getString(R.string.entrega).uppercase()
            }
            getString(R.string.devolucion) -> {
                accionElegidaInformacion.text = getString(R.string.devolucion).uppercase()
            }
            getString(R.string.localizacion) -> {
                accionElegidaInformacion.text = getString(R.string.localizacion).uppercase()
            }
        }
    }
}