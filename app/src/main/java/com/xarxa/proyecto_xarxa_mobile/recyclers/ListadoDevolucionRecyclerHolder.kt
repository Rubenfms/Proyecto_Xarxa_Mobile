package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.services.PasarPosicionInterface

class ListadoDevolucionRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaDevolucion)
    private var loteDevuelto: TextView = itemView.findViewById(R.id.devueltoTextView)
    private var loteCompleto: TextView = itemView.findViewById(R.id.completoTextView)

    fun bind(cadena: String) {
        nombreAlumno.text = cadena
    }
}