package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class ListadoDevolucionRecyclerAdapter(datos: ArrayList<Alumno>) :
    RecyclerView.Adapter<ListadoDevolucionRecyclerHolder>(), View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<Alumno> = datos
    private lateinit var clickListener: View.OnClickListener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListadoDevolucionRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lista_alu_devolucion, parent, false)

        val holder = ListadoDevolucionRecyclerHolder(view)
        view.setOnClickListener(this)

        return holder
    }

    override fun onBindViewHolder(holder: ListadoDevolucionRecyclerHolder, position: Int) {
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
}