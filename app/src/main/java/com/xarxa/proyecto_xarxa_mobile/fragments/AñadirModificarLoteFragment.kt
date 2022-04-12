package com.xarxa.proyecto_xarxa_mobile.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutAnadirModificarLoteBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.modelos.Modalidad
import com.xarxa.proyecto_xarxa_mobile.recyclers.LotesEntregaRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class AñadirModificarLoteFragment : Fragment(), SearchView.OnQueryTextListener {

    private var listaLotes: ArrayList<Lote> = ArrayList()
    private lateinit var _binding: LayoutAnadirModificarLoteBinding
    private val binding get() = _binding
    private var nia: Int = 0
    private var alumnoConLote: Boolean = false
    private var lotesAlumno: ArrayList<Lote> = ArrayList()
    private lateinit var adaptador: LotesEntregaRecyclerAdapter
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

        _binding = LayoutAnadirModificarLoteBinding.inflate(requireActivity().layoutInflater)
        val view = binding.root

        setHasOptionsMenu(true)
        navController = NavHostFragment.findNavController(this)
        recyclerView = binding.recyclerLotesEntrega
        adaptadorAPIRest = APIRestAdapter()
        recibirNIA()
        getLotesAlumno()
        getLotesOwnerless()

        return view
    }

    private fun getLotesAlumno() {
        CoroutineScope(Dispatchers.Main).launch {
            lotesAlumno =
                adaptadorAPIRest.getLotesByNiaAsync(nia).await()
            alumnoConLote = lotesAlumno.isNotEmpty()
        }
    }

    private fun getLotesOwnerless() {
        CoroutineScope(Dispatchers.Main).launch {
            listaLotes = adaptadorAPIRest.getLotesByNiaAsync(0).await()
            for (lote in listaLotes) {
                val modalidad: Modalidad =
                    adaptadorAPIRest.getModalidadByIdAsync(lote.idModalidad).await()
                lote.nombreModalidad = modalidad.nombre
            }
            cargarRecyclerLotes(listaLotes)
        }
    }

    private fun cargarRecyclerLotes(lista: ArrayList<Lote>) {
        adaptador = LotesEntregaRecyclerAdapter(lista)
        recyclerView.adapter = adaptador
        recyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        adaptador.clickListener {
            val posicion = recyclerView.getChildAdapterPosition(it)
            showPopup(recyclerView[posicion].findViewById(R.id.nombreLoteTextView), posicion)
        }
    }

    private fun respuestaPeticion(
        mensajeInfo: String,
        mensajeError: String,
        response: Response<Void>
    ) {
        if (response.isSuccessful) {
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
            Log.e("Error", response.errorBody()!!.string())
        }
    }

    private fun updateLote(posicion: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (alumnoConLote) {
                lotesAlumno[0].niaAlumno = null
                adaptadorAPIRest.updateLoteAsync(lotesAlumno[0]).await()
            }
            listaLotes[posicion].niaAlumno = nia
            val response = adaptadorAPIRest.updateLoteAsync(listaLotes[posicion]).await()
            respuestaPeticion(
                "Lote asignado correctamente",
                "Ha ocurrido un error asignando el lote",
                response
            )
            navController.popBackStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchview_menu, menu)
        menu.setGroupVisible(R.id.escanerGroup, false)
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

    override fun onQueryTextSubmit(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
    }

    override fun onQueryTextChange(textoAFiltrar: String): Boolean {
        filtrar(textoAFiltrar)
        return false
    }

    private fun filtrar(textoAFiltrar: String) {
        if (TextUtils.isEmpty(textoAFiltrar)) {
            cargarRecyclerLotes(listaLotes)
        } else {
            val listaFiltrada = ArrayList<Lote>()
            for (x in listaLotes) {
                val text = x.nombreModalidad.lowercase()
                if (text.contains(textoAFiltrar.lowercase())) listaFiltrada.add(x)
                else if (text.indexOf(textoAFiltrar.lowercase()) == 0) listaFiltrada.add(
                    x
                )
                cargarRecyclerLotes(listaFiltrada)
            }
        }
    }

    private fun showPopup(view: View, posicion: Int) {
        val menuEmergente = PopupMenu(requireActivity(), view)
        menuEmergente.inflate(R.menu.seleccion_lote_menu)
        menuEmergente.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.verLoteOption -> {
                    xarxaViewModel.setIdLote(listaLotes[posicion].idLote)
                    if (navController.currentDestination?.id == R.id.añadirModificarLoteFragment)
                        navController.navigate(R.id.action_añadirModificarLoteFragment_to_informacionLoteFragment)
                }
                R.id.seleccionarLoteOption -> {
                    mostrarDialogoPersonalizado(posicion)
                }
                R.id.cancelarOption -> menuEmergente.dismiss()
            }
            true
        }
        menuEmergente.show()
    }

    private fun mostrarDialogoPersonalizado(
        posicion: Int
    ) {
        val nombreModalidad = listaLotes[posicion].nombreModalidad
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val mensaje =
            if (alumnoConLote) "El alumno con NIA <b>$nia</b> ya tiene un lote asignado, si asignas un lote nuevo, se desasignará el anterior." +
                    " ¿Deseas asignar el nuevo lote <b>$nombreModalidad</b>?"
            else "¿Deseas asignar al alumno con NIA <b>$nia</b> el lote <b>$nombreModalidad</b>?"
        val mensajeHTML = HtmlCompat.fromHtml(mensaje, HtmlCompat.FROM_HTML_MODE_LEGACY)
        builder.setMessage(
            mensajeHTML
        )
            .setPositiveButton("Asignar") { _, _ ->
                updateLote(posicion)
            }
            .setNegativeButton("Cerrar") { _, _ ->
            }.show()
    }

    private fun recibirNIA() {
        val niaObserver = Observer<Int> { i -> nia = i }
        xarxaViewModel.getNia().observe(requireActivity(), niaObserver)
    }
}