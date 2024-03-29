package com.xarxa.proyecto_xarxa_mobile.fragments

import android.annotation.SuppressLint
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
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutGruposBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.recyclers.CursosGruposRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GruposFragment : Fragment() {

    private lateinit var _binding: LayoutGruposBinding
    private val binding get() = _binding
    private var listaGrupos: ArrayList<String> = ArrayList()
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: CursosGruposRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var accionElegida: String
    private lateinit var curso: String
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutGruposBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerGrupos
        adaptadorAPIRest = APIRestAdapter()
        recibirCurso()
        recibirAccionElegida()
        getAlumnos()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos =
                adaptadorAPIRest.getAlumnosByCursoAsync(curso, xarxaViewModel.getSessionIdString())
                    .await()
            logicaGrupos()
        }
    }

    private fun cargarRecyclerGrupos() {
        adaptador = CursosGruposRecyclerAdapter(listaGrupos)
        recyclerView.adapter = adaptador
        if (activity != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adaptador.onClickListener {
                val posicion = recyclerView.getChildAdapterPosition(it)
                xarxaViewModel.setGrupo(listaGrupos[posicion])
                if (navController.currentDestination?.id == R.id.gruposFragment) {
                    when (xarxaViewModel.getAccionElegida().value) {
                        getString(R.string.entrega) -> {
                            navController.navigate(R.id.action_gruposFragment_to_listadoAlumnosEntregaFragment)

                        }
                        getString(R.string.devolucion) -> {
                            navController.navigate(R.id.action_gruposFragment_to_listadoAlumnosDevolucionFragment)
                        }
                        getString(R.string.localizacion) -> {
                            navController.navigate(R.id.action_gruposFragment_to_localizacionFragment)
                        }
                    }
                }
            }
        }
    }

    private fun logicaGrupos() {
        for (i in 0 until listaAlumnos.size) {
            val grupoAlumno = listaAlumnos[i].grupo
            if (!listaGrupos.contains(grupoAlumno)) {
                listaGrupos.add(grupoAlumno)
            }
        }
        listaGrupos.sort()
        cargarRecyclerGrupos()
    }

    private fun recibirCurso() {
        val cursoObserver = Observer<String> { i -> curso = i }
        xarxaViewModel.getCurso().observe(requireActivity(), cursoObserver)
    }

    @SuppressLint("SetTextI18n")
    private fun recibirAccionElegida() {
        val accionObserver = Observer<String> { i -> accionElegida = i }
        xarxaViewModel.getAccionElegida().observe(requireActivity(), accionObserver)
        val accionElegidaInformacion = binding.informacionAccionGruposTextView
        when (xarxaViewModel.getAccionElegida().value) {
            getString(R.string.entrega) -> {
                accionElegidaInformacion.text =
                    "${getString(R.string.entrega).uppercase()} - $curso"
            }
            getString(R.string.devolucion) -> {
                accionElegidaInformacion.text =
                    "${getString(R.string.devolucion).uppercase()} - $curso"
            }
            getString(R.string.localizacion) -> {
                accionElegidaInformacion.text =
                    "${getString(R.string.localizacion).uppercase()} - $curso"
            }
        }
    }
}