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
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutInformacionAlumnoBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InformacionAlumnoFragment : Fragment() {

    private lateinit var _binding: LayoutInformacionAlumnoBinding
    private val binding get() = _binding
    private val xarxaViewModel: XarxaViewModel by activityViewModels()
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private lateinit var navController: NavController
    private var alumno = Alumno()
    private var nia: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutInformacionAlumnoBinding.inflate(inflater, container, false)
        val view = binding.root

        adaptadorAPIRest = APIRestAdapter()
        navController = NavHostFragment.findNavController(this)
        recibirNIA()
        getAlumno()

        return view
    }

    private fun getAlumno() {
        CoroutineScope(Dispatchers.Main).launch {
            alumno = adaptadorAPIRest.getAlumnoByNiaAsync(nia, xarxaViewModel.getSessionIdString())
                .await()
            rellenarControles()
        }
    }

    private fun rellenarControles() {
        val apellidos = "${alumno.apellido1} ${alumno.apellido2}"
        binding.niaAlumnoEditText.setText(alumno.nia.toString())
        binding.nombreAlumnoEditText.setText(alumno.nombre)
        binding.apellidosAlumnoEditText.setText(apellidos)
        binding.estadoMatAlumnoEditText.setText(alumno.estadoMatriculacion)
        binding.cursoAlumnoEditText.setText(alumno.curso)
        binding.grupoAlumnoEditText.setText(alumno.grupo)
        binding.perteneceXarxaCheckBox.isChecked = alumno.perteneceXarxa
        if (alumno.idLote != null) {
            binding.loteAlumnoEditText.setText(alumno.idLote.toString())
            binding.verLoteButton.visibility = View.VISIBLE
            binding.verLoteButton.setOnClickListener {
                mostrarDialogoPersonalizado()
            }
        }
        if (alumno.estadoLote.isNotEmpty()) {
            binding.estadoLoteEditText.setText(alumno.estadoLote)
        }
        if (!alumno.incidencias.isNullOrEmpty()) {
            binding.incidenciasAlumnoEditText.setText(alumno.incidencias)
        }
    }

    private fun mostrarDialogoPersonalizado(
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setMessage("¿Ver lote?")
            .setPositiveButton("Aceptar") { _, _ ->
                xarxaViewModel.setIdLote(alumno.idLote!!)
                if (navController.currentDestination?.id == R.id.informacionAlumnoFragment)
                    navController.navigate(R.id.action_informacionAlumnoFragment_to_informacionLoteFragment)
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