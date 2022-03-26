package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.services.PasarPosicionInterface

class ListadoDevolucionRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<ListadoDevolucionRecyclerHolder>(), View.OnLongClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var longClickListener: View.OnLongClickListener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListadoDevolucionRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lista_alu_devolucion, parent, false)

        val holder = ListadoDevolucionRecyclerHolder(view)
        view.setOnLongClickListener(this)

        return holder
    }

    override fun onBindViewHolder(holder: ListadoDevolucionRecyclerHolder, position: Int) {
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