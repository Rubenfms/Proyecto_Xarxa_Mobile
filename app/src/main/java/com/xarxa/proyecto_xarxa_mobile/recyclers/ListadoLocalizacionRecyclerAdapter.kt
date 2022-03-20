package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class ListadoLocalizacionRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<ListadoLocalizacionRecyclerAdapter.AlumnosLocalizacionRecyclerHolder>(),
    View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var clickListenerAlumnos: View.OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlumnosLocalizacionRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_lista_alu_localizacion, parent, false)
        view.setOnClickListener(this)

        return AlumnosLocalizacionRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: AlumnosLocalizacionRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun onClickListenerAlumnos(clickListener: View.OnClickListener) {
        clickListenerAlumnos = clickListener
    }

    override fun onClick(p0: View?) {
        clickListenerAlumnos.onClick(p0)
    }

    inner class AlumnosLocalizacionRecyclerHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var alumno: TextView = itemView.findViewById(R.id.nombreAlumnoListaLocalizacion)

        fun bind(cadena: String) {
            alumno.text = cadena
        }
    }
}