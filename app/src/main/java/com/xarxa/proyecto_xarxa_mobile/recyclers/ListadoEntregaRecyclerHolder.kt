package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class ListadoEntregaRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaEntrega)
    private var loteEntregado: TextView = itemView.findViewById(R.id.loteEntregadoTextView)

    fun bind(cadena: String) {
        nombreAlumno.text = cadena
        loteEntregado.text = "Sí"
    }

}