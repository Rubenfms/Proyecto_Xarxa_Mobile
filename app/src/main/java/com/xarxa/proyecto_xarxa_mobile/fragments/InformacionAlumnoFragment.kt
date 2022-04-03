package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
        recibirNIA()
        getAlumno()

        return view
    }

    private fun getAlumno() {
        CoroutineScope(Dispatchers.Main).launch {
            alumno = adaptadorAPIRest.getAlumnoByNiaAsync(nia).await()
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
    }

    private fun recibirNIA() {
        val niaObserver = Observer<Int> { i -> nia = i }
        xarxaViewModel.getNia().observe(requireActivity(), niaObserver)
    }
}