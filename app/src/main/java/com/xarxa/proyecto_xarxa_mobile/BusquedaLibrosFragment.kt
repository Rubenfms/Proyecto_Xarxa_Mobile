package com.xarxa.proyecto_xarxa_mobile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutBusquedaLibrosBinding

class BusquedaLibrosFragment : Fragment() {

    private lateinit var _binding: LayoutBusquedaLibrosBinding
    private val binding get() = _binding
    private lateinit var registerPermisosCamera: ActivityResultLauncher<String>
    private lateinit var resultadoCamara: ActivityResultLauncher<Intent>
    private lateinit var codigoLeido: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = LayoutBusquedaLibrosBinding.inflate(inflater, container, false)

        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.busquedaLibrosOption).isChecked =
            true

        creaContrato()
        crearContratoPermisosYAbreCamara()
        codigoLeido = binding.codigoBarrasTextView

        binding.escanerButton.setOnClickListener {
            registerPermisosCamera.launch(Manifest.permission.CAMERA)
        }

        return view
    }

    private fun crearContratoPermisosYAbreCamara() {
        registerPermisosCamera =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it == true) escanear()
                else {
                    Toast.makeText(
                        requireActivity(),
                        "No puedes escanear si no das permiso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun creaContrato() {
        resultadoCamara =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    codigoLeido.text = result.data!!.getStringExtra("codigo")
                }
            }
    }

    private fun escanear() {
        val cameraIntent = Intent(requireActivity(), CodigoBarrasScanner::class.java)
        resultadoCamara.launch(cameraIntent)
    }
}