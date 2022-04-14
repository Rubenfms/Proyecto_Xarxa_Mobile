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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
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
    private lateinit var grupo: String
    private lateinit var chipNia: Chip
    private lateinit var chipNombre: Chip
    private lateinit var chipLote: Chip
    private lateinit var editTextBusqueda: EditText
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
        chipNia = binding.chipNIA
        chipNombre = binding.chipNombre
        chipLote = binding.chipLote

        recibirGrupo()
        getAlumnos()
        creaContrato()
        crearContratoPermisosYAbreCamara()
        logicaChips()

        return view
    }

    private fun getAlumnos() {
        CoroutineScope(Dispatchers.Main).launch {
            listaAlumnos = adaptadorAPIRest.getAlumnosByGrupoAsync(grupo).await()
            cargarRecyclerAlumnos(listaAlumnos)
        }
    }

    private fun recibirGrupo() {
        val grupoObserver = Observer<String> { i -> grupo = i }
        xarxaViewModel.getGrupo().observe(requireActivity(), grupoObserver)
        binding.cursoActualTextView.text = grupo
    }

    private fun logicaChips() {
        binding.chipGroup.setOnCheckedChangeListener { group, _ ->
            when (group.checkedChipId) {
                R.id.chipNIA -> {
                    chipNia.isChecked = true
                    filtrar(editTextBusqueda.text.toString())
                }
                R.id.chipNombre -> {
                    chipNombre.isChecked = true
                    filtrar(editTextBusqueda.text.toString())

                }
                R.id.chipLote -> {
                    chipLote.isChecked = true
                    filtrar(editTextBusqueda.text.toString())
                }
                else -> {
                    chipNia.isChecked = true
                }
            }
        }
    }

    private fun cargarRecyclerAlumnos(listaAlumnos: ArrayList<Alumno>) {
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
        editTextBusqueda = searchItem.findViewById(androidx.appcompat.R.id.search_src_text)
        editTextBusqueda.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.primaryTextColor
            )
        )
        editTextBusqueda.setHintTextColor(
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
                    val resultado = result.data!!.getStringExtra("codigo").toString()
                    chipLote.isChecked = true
                    filtrar(resultado)
                    Snackbar.make(
                        requireActivity().findViewById(R.id.fragmentContainer),
                        "Filtrando alumno con número del lote: ($resultado)...",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun escanear() {
        val cameraIntent = Intent(requireActivity(), CodigoBarrasScanner::class.java)
        resultadoCamara.launch(cameraIntent)
    }

    private fun filtrar(textoAFiltrar: String) {
        val textoFiltrarMinus = textoAFiltrar.lowercase()
        if (TextUtils.isEmpty(textoAFiltrar)) {
            cargarRecyclerAlumnos(listaAlumnos)
        } else {
            val listaFiltrada = ArrayList<Alumno>()
            for (x in listaAlumnos) {
                when {
                    chipNia.isChecked -> {
                        val niaAlumno = x.nia.toString().lowercase()
                        if (niaAlumno.contains(textoFiltrarMinus)) {
                            listaFiltrada.add(x)
                        } else if (niaAlumno.indexOf(textoFiltrarMinus) == 0) {
                            listaFiltrada.add(x)
                        }
                    }
                    chipNombre.isChecked -> {
                        val nombreAlumno = x.nombre.lowercase()
                        if (nombreAlumno.contains(textoFiltrarMinus)) {
                            listaFiltrada.add(x)
                        } else if (nombreAlumno.indexOf(textoFiltrarMinus) == 0) {
                            listaFiltrada.add(x)
                        }
                    }
                    chipLote.isChecked -> {
                        if (x.loteCollection.isNotEmpty()) {
                            val lote = x.loteCollection[0].idLote.toString().lowercase()
                            if (lote.contains(textoFiltrarMinus)) {
                                listaFiltrada.add(x)
                            } else if (lote.indexOf(textoFiltrarMinus) == 0) {
                                listaFiltrada.add(x)
                            }
                        }
                    }
                }
                cargarRecyclerAlumnos(listaFiltrada)
            }
        }
    }
}