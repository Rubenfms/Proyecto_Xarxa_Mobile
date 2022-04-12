package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.xarxa.proyecto_xarxa_mobile.R
import com.xarxa.proyecto_xarxa_mobile.modelos.Xarxa
import com.xarxa.proyecto_xarxa_mobile.services.PasarDatosInterface

class LibrosDevolucionRecyclerAdapter(datos: ArrayList<Xarxa>) :
    RecyclerView.Adapter<LibrosDevolucionRecyclerAdapter.LibrosRecyclerHolder>() {

    private lateinit var view: View
    private var datos: ArrayList<Xarxa> = datos
    private lateinit var pasarDatosInterface: PasarDatosInterface

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LibrosRecyclerHolder {
        view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_row_libros_devolucion, parent, false)

        val holder = LibrosRecyclerHolder(view)

        holder.onItemCheckListener(object : PasarDatosInterface {
            override fun pasarLibro(libro: Xarxa, checkeado: Boolean) {
                pasarDatosInterface.pasarLibro(libro, checkeado)
            }
        })

        return holder
    }

    override fun onBindViewHolder(holder: LibrosRecyclerHolder, position: Int) {
        holder.bind(datos[position])
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    fun pasarLibro(pasarDatosInterface: PasarDatosInterface) {
        this.pasarDatosInterface = pasarDatosInterface
    }


    inner class LibrosRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private var libro: CheckBox = itemView.findViewById(R.id.nombreLibroTextView)
        private lateinit var pasarDatosInterface: PasarDatosInterface
        private lateinit var xarxa: Xarxa

        init {
            libro.setOnClickListener(this)
        }

        fun bind(xarxa: Xarxa) {
            this.xarxa = xarxa
            libro.text = xarxa.isbn.titulo
        }

        fun onItemCheckListener(check: PasarDatosInterface) {
            pasarDatosInterface = check
        }

        override fun onClick(p0: View?) {
            if (libro.isChecked) {
                pasarDatosInterface.pasarLibro(xarxa, true)
            } else if (!libro.isChecked) {
                pasarDatosInterface.pasarLibro(xarxa, false)
            }
        }
    }

}