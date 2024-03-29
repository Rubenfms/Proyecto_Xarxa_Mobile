package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Libro

class LibrosInformacionLoteRecyclerAdapter(datos: ArrayList<Libro>) :
    RecyclerView.Adapter<LibrosInformacionLoteRecyclerAdapter.LibrosRecyclerHolder>() {

    private lateinit var view: View
    private var datos: ArrayList<Libro> = datos

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibrosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_libros_informacion_lote, parent, false)

        return LibrosRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: LibrosRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    inner class LibrosRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var nombre: TextView = itemView.findViewById(R.id.nombreLibroTextView)
        private var curso: TextView = itemView.findViewById(R.id.cursoLibroTextView)
        private var editorial: TextView = itemView.findViewById(R.id.editorialLibroTextView)


        fun bind(libro: Libro) {
            nombre.text = libro.titulo
            curso.text = libro.curso
            editorial.text = libro.editorial
        }
    }
}