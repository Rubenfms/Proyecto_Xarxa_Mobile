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
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutCheckeoIncidenciasDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.recyclers.LibrosDevolucionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckeoLibrosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutCheckeoIncidenciasDevolucionBinding
    private val binding get() = _binding
    private lateinit var adaptador: LibrosDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()
    private var alumno = Alumno()
    private var nia: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutCheckeoIncidenciasDevolucionBinding.inflate(inflater, container, false)
        val view = binding.root

        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerLibrosDevolucion
        adaptadorAPIRest = APIRestAdapter()
        recibirNIA()
        getAlumno()

        binding.aceptarButton.setOnClickListener {
            mostrarDialogoPersonalizado()
        }
        return view
    }

    private fun getAlumno() {
        CoroutineScope(Dispatchers.Main).launch {
            alumno = adaptadorAPIRest.getAlumnoByNiaAsync(nia).await()
            cargarRecyclerCursos()
        }
    }

    private fun cargarRecyclerCursos() {
        adaptador = LibrosDevolucionRecyclerAdapter(alumno.loteCollection[0].xarxaCollection)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    private fun mostrarDialogoPersonalizado(
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)

        builder.setMessage(
            "¿Datos correctos?"
        )
            .setPositiveButton("Aceptar") { _, _ ->
                if (navController.currentDestination?.id == R.id.checkeoLibrosDevolucionFragment)
                    navController.navigate(R.id.action_global_listadoAlumnosDevolucionFragment)
            }
            .setNegativeButton("CANCELAR")
            { _, _ -> }
            .show()
    }

    private fun recibirNIA() {
        val niaObserver = Observer<Int> { i -> nia = i }
        xarxaViewModel.getNia().observe(requireActivity(), niaObserver)
    }
}