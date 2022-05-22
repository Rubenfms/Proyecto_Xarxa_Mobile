package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
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
import com.google.android.material.snackbar.Snackbar
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutCheckeoIncidenciasDevolucionBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Xarxa
import com.xarxa.proyecto_xarxa_mobile.recyclers.LibrosDevolucionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.PasarDatosInterface
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class CheckeoLibrosDevolucionFragment : Fragment() {

    private lateinit var _binding: LayoutCheckeoIncidenciasDevolucionBinding
    private val binding get() = _binding
    private lateinit var adaptador: LibrosDevolucionRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private var listaCheckeoLibros: ArrayList<Xarxa> = ArrayList()
    private val xarxaViewModel: XarxaViewModel by activityViewModels()
    private var alumno = Alumno()
    private var lote = Lote()
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
            alumno = adaptadorAPIRest.getAlumnoByNiaAsync(nia, xarxaViewModel.getSessionIdString())
                .await()
            lote = adaptadorAPIRest.getLoteByIdAsync(
                alumno.idLote!!,
                xarxaViewModel.getSessionIdString()
            ).await()
            cargarRecyclerCursos()
        }
    }

    private fun updateLote() {
        CoroutineScope(Dispatchers.Main).launch {
            val loteCompleto = listaCheckeoLibros.map { it.codigoXarxa }
                .sorted() == lote.librosLote.map { it.codigoXarxa }
                .sorted()
            lote.niaAlumno = null
            alumno.estadoLote = "Devuelto${if (loteCompleto) " completo" else ""}"
            val incidenciasObservaciones = binding.observacionesDevolucionLibrosEditText.text
            if (incidenciasObservaciones!!.length >= 5) {
                alumno.incidencias = incidenciasObservaciones.toString()
            }
            val response1 =
                adaptadorAPIRest.updateLoteAsync(lote, xarxaViewModel.getSessionIdString()).await()
            val response2 =
                adaptadorAPIRest.updateAlumnoAsync(alumno, xarxaViewModel.getSessionIdString())
                    .await()
            respuestaPeticion(
                "Lote devuelto correctamente",
                "Ha ocurrido un error devolviendo el lote",
                response1,
                response2
            )
            navController.popBackStack()
        }
    }

    private fun respuestaPeticion(
        mensajeInfo: String,
        mensajeError: String,
        response1: Response<Void>,
        response2: Response<Void>
    ) {
        if (response1.isSuccessful && response2.isSuccessful) {
            Snackbar.make(
                requireActivity().findViewById(R.id.fragmentContainer),
                mensajeInfo,
                Snackbar.LENGTH_LONG
            ).show()
        } else {
            Snackbar.make(
                requireActivity().findViewById(R.id.fragmentContainer),
                mensajeError,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun cargarRecyclerCursos() {
        listaCheckeoLibros = ArrayList(lote.librosLote)
        adaptador = LibrosDevolucionRecyclerAdapter(listaCheckeoLibros)
        recyclerView.adapter = adaptador
        if (activity != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

            adaptador.pasarLibro(object : PasarDatosInterface {
                override fun pasarLibro(libro: Xarxa, checkeado: Boolean) {
                    if (checkeado) {
                        listaCheckeoLibros.add(libro)
                    } else {
                        listaCheckeoLibros.remove(libro)
                    }
                }
            })
        }
    }

    private fun mostrarDialogoPersonalizado(
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage(
            "¿Datos correctos?"
        )
            .setPositiveButton("Aceptar") { _, _ ->
                updateLote()
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