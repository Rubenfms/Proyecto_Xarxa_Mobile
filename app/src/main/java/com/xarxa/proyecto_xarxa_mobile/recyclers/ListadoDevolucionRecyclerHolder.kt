package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class ListadoDevolucionRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var nombreApellidos: TextView =
        itemView.findViewById(R.id.nombreAlumnoListaDevolucion)
    private var nia: TextView = itemView.findViewById(R.id.niaAlumnoListaDevolucion)
    private var loteDevuelto: TextView = itemView.findViewById(R.id.devueltoTextView)
    private var loteCompleto: TextView = itemView.findViewById(R.id.completoTextView)

    fun bind(alumno: Alumno) {
        nombreApellidos.text = "${alumno.nombre} ${alumno.apellido1.substring(0, 1)}"
        nia.text = alumno.nia.toString()
        if (alumno.idLote != null) loteDevuelto.text = "No"
        else if (alumno.estadoLote.lowercase().contains("devuelto")) {
            loteDevuelto.text = "Sí"
            if (alumno.estadoLote.lowercase().contains("completo")) loteCompleto.text = "Sí"
            else loteCompleto.text = "No"
        }
    }
}
