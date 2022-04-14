package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.text.TextUtils.substring
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class ListadoLocalizacionRecyclerAdapter(datos: ArrayList<Alumno>) :
    RecyclerView.Adapter<ListadoLocalizacionRecyclerAdapter.AlumnosLocalizacionRecyclerHolder>(),
    View.OnClickListener {

    private lateinit var view: View
    private var listaAlumnos: ArrayList<Alumno> = datos
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
        holder.bind(listaAlumnos[position])
    }

    override fun getItemCount(): Int {
        return listaAlumnos.size
    }

    fun onClickListenerAlumnos(clickListener: View.OnClickListener) {
        clickListenerAlumnos = clickListener
    }

    override fun onClick(p0: View?) {
        clickListenerAlumnos.onClick(p0)
    }

    inner class AlumnosLocalizacionRecyclerHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var nombreApellidos: TextView =
            itemView.findViewById(R.id.nombreRowTextView)
        private var nia: TextView = itemView.findViewById(R.id.niaAlumnoRowTextView)
        private var lote: TextView = itemView.findViewById(R.id.loteRowTextView)


        fun bind(alumno: Alumno) {
            nombreApellidos.text = "${alumno.nombre} ${alumno.apellido1.substring(0, 1)}"
            nia.text = alumno.nia.toString()
            if (alumno.loteCollection.isNotEmpty()) {
                lote.text = alumno.loteCollection[0].idLote.toString()
            }
        }
    }
}