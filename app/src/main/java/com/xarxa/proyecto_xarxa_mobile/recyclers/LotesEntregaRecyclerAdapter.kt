package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Lote

class LotesEntregaRecyclerAdapter(datos: ArrayList<Lote>) :
    RecyclerView.Adapter<LotesEntregaRecyclerAdapter.LotesRecyclerHolder>(), View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<Lote> = datos
    private lateinit var clickListener: View.OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotesRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lotes_entrega, parent, false)
        view.setOnClickListener(this)
        return LotesRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: LotesRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun clickListener(listener: View.OnClickListener) {
        clickListener = listener
    }

    override fun onClick(p0: View?) {
        clickListener.onClick(p0)
    }

    inner class LotesRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var lote: TextView = itemView.findViewById(R.id.nombreLoteTextView)

        fun bind(l: Lote) {
            lote.text = l.modalidadLote.nombre
        }
    }


}