package com.xarxa.proyecto_xarxa_mobile.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.CodigoBarrasScanner
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusquedaAlumnoFragment : Fragment() {
    private lateinit var registerPermisosCamera: ActivityResultLauncher<String>
    private lateinit var resultadoCamara: ActivityResultLauncher<Intent>
    private val xarxaViewModel: XarxaViewModel by activityViewModels()
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private var lote: Lote = Lote()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        navController = NavHostFragment.findNavController(this)
        adaptadorAPIRest = APIRestAdapter()
        creaContrato()
        crearContratoPermisosYAbreCamara()
        registerPermisosCamera.launch(Manifest.permission.CAMERA)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun getLote(idLote: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            lote = adaptadorAPIRest.getLoteByIdAsync(idLote, xarxaViewModel.getSessionIdString()).await()
            val textoSnackbar: String
            if (lote.niaAlumno != null) {
                xarxaViewModel.setNia(lote.niaAlumno!!)
                if (navController.currentDestination?.id == R.id.busquedaAlumnoFragment)
                    navController.navigate(R.id.action_busquedaAlumnoFragment_to_informacionAlumnoFragment)
                textoSnackbar = "Filtrando alumno con número del lote: ($idLote)..."

            } else {
                textoSnackbar = "El lote buscado no tiene ningún alumno asignado."
                navController.navigate(R.id.principalFragment)
            }
            Snackbar.make(
                requireActivity().findViewById(R.id.fragmentContainer),
                textoSnackbar,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun crearContratoPermisosYAbreCamara() {
        registerPermisosCamera =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == true) escanear()
                else {
                    Toast.makeText(
                        requireActivity(),
                        "No puedes usar el escáner si no concedes los permisos de cámara",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(R.id.action_global_principalFragment)
                }
            }
    }

    private fun creaContrato() {
        resultadoCamara =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultado = result.data!!.getStringExtra("codigo").toString().toInt()
                    getLote(resultado)
                } else {
                    navController.navigate(R.id.action_global_principalFragment)
                }
            }
    }

    private fun escanear() {
        val cameraIntent = Intent(requireActivity(), CodigoBarrasScanner::class.java)
        resultadoCamara.launch(cameraIntent)
    }
}