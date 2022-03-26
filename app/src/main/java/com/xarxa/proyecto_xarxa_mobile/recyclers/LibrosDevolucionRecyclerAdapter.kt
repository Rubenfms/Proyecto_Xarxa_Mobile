package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class LibrosDevolucionRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<LibrosDevolucionRecyclerAdapter.LibrosRecyclerHolder>() {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibrosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_libros_devolucion, parent, false)

        return LibrosRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: LibrosRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class LibrosRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var libro: CheckBox = itemView.findViewById(R.id.nombreLibroTextView)

        fun bind(cadena: String) {
            libro.text = cadena
        }
    }

}