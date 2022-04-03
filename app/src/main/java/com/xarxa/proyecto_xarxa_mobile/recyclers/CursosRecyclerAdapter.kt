package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class CursosRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<CursosRecyclerAdapter.CursosRecyclerHolder>(), View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var clickListenerCursos: View.OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CursosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_row_cursos, parent, false)
        view.setOnClickListener(this)

        return CursosRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: CursosRecyclerHolder, position: Int) {
        var visibilidadLinea = false
        if (position + 1 != datos.size) {
            if (datos[position][0] != datos[position + 1][0]) {
                visibilidadLinea = true
            }
        }
        holder.bind(datos[position], visibilidadLinea)

    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class CursosRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var curso: TextView = itemView.findViewById(R.id.cursosTextView)
        private var linea: View = itemView.findViewById(R.id.linea)

        fun bind(cadena: String, visibilidadLinea: Boolean) {
            curso.text = cadena
            if (visibilidadLinea)
                linea.visibility = View.VISIBLE
        }
    }

    fun onClickListenerCursos(clickListener: View.OnClickListener) {
        clickListenerCursos = clickListener
    }

    override fun onClick(p0: View?) {
        clickListenerCursos.onClick(p0)
    }


}