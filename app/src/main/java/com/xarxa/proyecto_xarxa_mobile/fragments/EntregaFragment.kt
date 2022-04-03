package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutEntregaBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.recyclers.CursosRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntregaFragment : Fragment() {

    private lateinit var _binding: LayoutEntregaBinding
    private val binding get() = _binding
    private var listaCursos: ArrayList<String> = ArrayList()
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: CursosRecyclerAdapter
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

        _binding = LayoutEntregaBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.entregaOption).isChecked =
            true

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerCursosEntrega
        adaptadorAPIRest = APIRestAdapter()
        getAlumnos()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosAsync().await()
            logicaCursos()
        }
    }

    private fun cargarRecyclerCursos() {
        adaptador = CursosRecyclerAdapter(listaCursos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.onClickListenerCursos {
            val posicion = recyclerView.getChildAdapterPosition(it)
            xarxaViewModel.setCurso(listaCursos[posicion])
            if (navController.currentDestination?.id == R.id.entregaFragment)
                navController.navigate(R.id.action_entregaFragment_to_listadoAlumnosEntregaFragment)
        }
    }

    private fun logicaCursos() {
        for (i in 0 until listaAlumnos.size) {
            if (!listaCursos.contains(listaAlumnos[i].grupo)) {
                listaCursos.add(listaAlumnos[i].grupo)
            }
        }
        listaCursos.sort()
        cargarRecyclerCursos()
    }
}