package com.xarxa.proyecto_xarxa_mobile

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListadoEntregaRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreAlumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaEntrega)
    private var loteEntregado: CheckBox = itemView.findViewById(R.id.loteEntregadoCheckBox)

    fun bind(cadena: String) {
        nombreAlumno.text = cadena
        loteEntregado.isChecked = false // HAY QUE CAMBIARLO
    }

}