package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class ListadoEntregaRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<ListadoEntregaRecyclerHolder>(), View.OnLongClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var longClickListener: View.OnLongClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListadoEntregaRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lista_alu_entrega, parent, false)

        view.setOnLongClickListener(this)

        return ListadoEntregaRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: ListadoEntregaRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun longClick(listener: View.OnLongClickListener) {
        longClickListener = listener
    }

    override fun onLongClick(p0: View?): Boolean {
        longClickListener.onLongClick(p0)
        return false
    }
}