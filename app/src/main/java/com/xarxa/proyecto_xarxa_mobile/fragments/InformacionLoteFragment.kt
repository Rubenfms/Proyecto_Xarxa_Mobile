package com.xarxa.proyecto_xarxa_mobile.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutInformacionLoteBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote
import com.xarxa.proyecto_xarxa_mobile.recyclers.LibrosInformacionLoteRecyclerAdapter
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import com.xarxa.proyecto_xarxa_mobile.services.XarxaViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InformacionLoteFragment : Fragment() {

    private lateinit var _binding: LayoutInformacionLoteBinding
    private val binding get() = _binding
    private lateinit var adaptador: LibrosInformacionLoteRecyclerAdapter
    private lateinit var recyclerView: RecyclerView
    private var idLote: Int = 0
    private var lote = Lote()
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private val xarxaViewModel: XarxaViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = LayoutInformacionLoteBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.recyclerLibrosLote
        adaptadorAPIRest = APIRestAdapter()
        recibirIDLote()
        getLote()

        return view
    }

    private fun getLote() {
        CoroutineScope(Dispatchers.Main).launch {
            lote = adaptadorAPIRest.getLoteByIdAsync(idLote, xarxaViewModel.getSessionIdString())
                .await()
            cargarRecycler()
        }
    }

    private fun cargarRecycler() {
        adaptador = LibrosInformacionLoteRecyclerAdapter(lote.modalidadLote.libroCollection)
        recyclerView.adapter = adaptador
        if (activity != null) {
            recyclerView.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun recibirIDLote() {
        val idObserver = Observer<Int> { i -> idLote = i }
        xarxaViewModel.getIdLote().observe(requireActivity(), idObserver)
        binding.informacionLoteTextView.text = "Lote con ID $idLote"
    }

}