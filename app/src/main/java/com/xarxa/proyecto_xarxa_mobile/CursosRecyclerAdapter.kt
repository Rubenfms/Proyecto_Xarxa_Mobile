package com.xarxa.proyecto_xarxa_mobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CursosRecyclerAdapter(datos: ArrayList<String>) :
    RecyclerView.Adapter<CursosRecyclerAdapter.CursosRecyclerHolder>(), View.OnClickListener {

    private lateinit var view: View
    private var datos: ArrayList<String> = datos
    private lateinit var clickListenerCursos : View.OnClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CursosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_row_entrega, parent, false)
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

    fun onClickListenerCursos(clickListener: View.OnClickListener) {
        clickListenerCursos = clickListener
    }

    override fun onClick(p0: View?) {
        clickListenerCursos.onClick(p0)
    }


}