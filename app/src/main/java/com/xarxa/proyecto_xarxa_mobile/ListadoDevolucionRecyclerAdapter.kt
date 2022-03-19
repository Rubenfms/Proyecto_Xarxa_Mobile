package com.xarxa.proyecto_xarxa_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListadoDevolucionRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<ListadoDevolucionRecyclerHolder>() {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListadoDevolucionRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lista_alu_devolucion, parent, false)

        return ListadoDevolucionRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: ListadoDevolucionRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }
}