package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class ListadoDevolucionRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaDevolucion)
    private var loteDevuelto: CheckBox = itemView.findViewById(R.id.devueltoCheckBox)
    private var loteCompleto: CheckBox = itemView.findViewById(R.id.completoCheckBox)

    fun bind(cadena: String) {
        nombreAlumno.text = cadena
        loteDevuelto.isChecked = false
        loteCompleto.isChecked = false // HAY QUE CAMBIARLO
    }

}