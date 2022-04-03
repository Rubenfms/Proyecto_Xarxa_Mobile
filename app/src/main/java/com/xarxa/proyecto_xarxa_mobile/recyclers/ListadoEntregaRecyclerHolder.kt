package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class ListadoEntregaRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaEntrega)
    private var loteEntregado: TextView = itemView.findViewById(R.id.loteEntregadoTextView)

    fun bind(alumno: Alumno) {
        nombreAlumno.text = "${alumno.nombre} ${alumno.apellido1}"
        if (alumno.loteCollection.isEmpty()) loteEntregado.text = "No" else loteEntregado.text =
            "Sí"
    }

}