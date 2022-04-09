package com.xarxa.proyecto_xarxa_mobile.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutLocalizacionBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno
import com.xarxa.proyecto_xarxa_mobile.recyclers.ListadoLocalizacionRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.CodigoBarrasScanner
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocalizacionFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var _binding: LayoutLocalizacionBinding
    private val binding get() = _binding
    private var listaAlumnos: ArrayList<Alumno> = ArrayList()
    private lateinit var adaptador: ListadoLocalizacionRecyclerAdapter
    private lateinit var registerPermisosCamera: ActivityResultLauncher<String>
    private lateinit var resultadoCamara: ActivityResultLauncher<Intent>
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutLocalizacionBinding.inflate(inflater, container, false)
        val view = binding.root
        requireActivity().findViewById<NavigationView>(R.id.navigationView).menu.findItem(R.id.localizacionOption).isChecked =
            true
        setHasOptionsMenu(true)
        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerAlumnosLocalizacion
        adaptadorAPIRest = APIRestAdapter()
        getAlumnos()

        creaContrato()
        crearContratoPermisosYAbreCamara()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosAsync().await()
            listaAlumnos.sortBy { it.grupo }
            cargarRecyclerAlumnos()
        }
    }

    private fun cargarRecyclerAlumnos() {
        adaptador = ListadoLocalizacionRecyclerAdapter(listaAlumnos)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.onClickListenerAlumnos {
            val posicion = recyclerView.getChildAdapterPosition(it)
            xarxaViewModel.setNia(listaAlumnos[posicion].nia)
            if (navController.currentDestination?.id == R.id.localizacionFragment)
                navController.navigate(R.id.action_localizacionFragment_to_informacionAlumnoFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchview_menu, menu)
        val searchItem = menu.findItem(R.id.actionSearch).actionView as SearchView
        searchItem.setOnQueryTextListener(this)
        val editText = searchItem.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        editText.setTextColor(ContextCompat.getColor(requireActivity(), R.color.primaryTextColor))
        editText.setHintTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.primaryTextColor
            )
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.escanearOption -> registerPermisosCamera.launch(Manifest.permission.CAMERA)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
    }

    override fun onQueryTextChange(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
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
                }
            }
    }

    private fun creaContrato() {
        resultadoCamara =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // AQUÍ SE SALTARÁ AL FRAGMENT INFORMACIÓN ALUMNMO
                    val resultado = result.data!!.getStringExtra("codigo").toString()
                    Snackbar.make(
                        requireActivity().findViewById(R.id.fragmentContainer),
                        "Has filtrado un alumno por el código de un libro ($resultado)",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun escanear() {
        val cameraIntent = Intent(requireActivity(), CodigoBarrasScanner::class.java)
        resultadoCamara.launch(cameraIntent)
    }

    // HAY QUE CAMBIAR EL MÉTODO DE FILTRADO PARA QUE SEA POR NIA, LOTE Y NOMBRE
    private fun filtrar(textoAFiltrar: String) {
        if (TextUtils.isEmpty(textoAFiltrar)) {
            cargarRecyclerAlumnos()
        } else {
            val listaFiltrada = ArrayList<Alumno>()
            for (x in listaAlumnos) {
                val text = x.nombre.lowercase()
                if (text.contains(textoAFiltrar.lowercase())) listaFiltrada.add(x)
                else if (text.indexOf(textoAFiltrar.lowercase()) == 0) listaFiltrada.add(x)
                adaptador = ListadoLocalizacionRecyclerAdapter(listaFiltrada)
                recyclerView.adapter = adaptador
            }
        }
    }

}