package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class LotesEntregaRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<LotesEntregaRecyclerAdapter.LotesRecyclerHolder>() {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotesRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lotes_entrega, parent, false)

        return LotesRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: LotesRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class LotesRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var lote: TextView = itemView.findViewById(R.id.nombreLoteTextView)

        fun bind(cadena: String) {
            lote.text = cadena
        }
    }
}