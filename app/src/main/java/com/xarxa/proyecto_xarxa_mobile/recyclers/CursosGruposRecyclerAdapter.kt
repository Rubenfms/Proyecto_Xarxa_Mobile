package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R

class CursosGruposRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<CursosGruposRecyclerAdapter.CursosRecyclerHolder>(), View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var clickListener: View.OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CursosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_cursos_grupos, parent, false)
        view.setOnClickListener(this)

        return CursosRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: CursosRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class CursosRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var curso: TextView = itemView.findViewById(R.id.cursosTextView)

        fun bind(cadena: String) {
            curso.text = cadena
        }
    }

    fun onClickListener(clickListener: View.OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onClick(p0: View?) {
        clickListener.onClick(p0)
    }


}