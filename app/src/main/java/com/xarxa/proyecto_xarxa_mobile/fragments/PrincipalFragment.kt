package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutPrincipalBinding
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel

class PrincipalFragment : Fragment() {

    private lateinit var _binding: LayoutPrincipalBinding
    private val binding get() = _binding
    private lateinit var navController: NavController
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = LayoutPrincipalBinding.inflate(inflater, container, false)
        val view = binding.root
        val navigationView = requireActivity().findViewById<NavigationView>(R.id.navigationView)
        navigationView.menu.findItem(R.id.principalOption).isChecked = true
        navController = NavHostFragment.findNavController(this)

        binding.entregaButton.setOnClickListener {
            if (navController.currentDestination?.id == R.id.principalFragment) {
                navController.navigate(R.id.action_principalFragment_to_cursosFragment)
                xarxaViewModel.setAccionElegida(getString(R.string.entrega))
                navigationView.menu.findItem(R.id.entregaOption).isChecked =
                    true
            }
        }

        binding.devolucionButton.setOnClickListener {
            if (navController.currentDestination?.id == R.id.principalFragment) {
                navController.navigate(R.id.action_principalFragment_to_cursosFragment)
                navigationView.menu.findItem(R.id.devolucionOption).isChecked =
                    true
                xarxaViewModel.setAccionElegida(getString(R.string.devolucion))
            }
        }
        return view
    }
}