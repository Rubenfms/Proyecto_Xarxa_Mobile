package com.xarxa.proyecto_xarxa_mobile.recyclers

import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView

abstract class SQLiteCursorRecyclerAdapter (cursor: Cursor) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var holderCursor: Cursor = cursor

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        check(holderCursor.moveToPosition(position)) {
            "ERROR, no se puede encontrar la posición: $position"
        }
        onBindViewHolder(holder, holderCursor)
    }

    abstract fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        cursor: Cursor
    )

    override fun getItemCount(): Int {
        return holderCursor.count
    }


}