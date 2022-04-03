package com.xarxa.proyecto_xarxa_mobile.services

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.xarxa.proyecto_xarxa_mobile.modelos.Alumno

class SQLiteAdapter(var context: Context) {

    private var xarxaBD: BDXarxa = BDXarxa(context, "BDXarxa", null, 1)

    fun clearXarxa() {
        var dbImagenes = xarxaBD.writableDatabase
        dbImagenes.delete("alumno", null, null)
        dbImagenes.close()
    }

    fun insertarDatosAlumnos(listaAlumnos: ArrayList<Alumno>) {
        var xarxa = xarxaBD.writableDatabase
        if (xarxa != null) {
            for (alumno in listaAlumnos) {
                // ESTÁ SIN LA FECHA DE NACIMIENTO
                val sentencia =
                    "INSERT INTO alumno (nia, nombre, apellido1, apellido2, estado_matriculacion, curso, grupo, incidencias, pertenece_xarxa) VALUES " +
                            " ('" + alumno.nia + "','" + alumno.nombre + "','" + alumno.apellido1 + "'" +
                            ",'" + alumno.apellido2 + "','" + alumno.estadoMatriculacion + "','" + alumno.curso +
                            "','" + alumno.grupo + "','" + "','" + alumno.incidencias + "','" + "','" + alumno.perteneceXarxa + "');"
                xarxa.execSQL(sentencia)
            }
            xarxa.close()
        }
    }

    fun getAlumnos(): Cursor? {
        val accesoBDClientes = xarxaBD.readableDatabase
        if (accesoBDClientes != null) {
            return accesoBDClientes.rawQuery(
                "SELECT * FROM alumno;",
                null
            )
        }
        return null
    }

    fun getAlumnos(
        columnas: Array<String?>?,
        where: String?,
        valores: Array<String?>?,
        orderBy: String?
    ): Cursor? {
        val xarxa = xarxaBD.readableDatabase
        if (xarxa != null) {
            val cursor: Cursor =
                xarxa.query("alumno", columnas, where, valores, null, null, null)
            return cursor
        }
        return null
    }

    internal inner class BDXarxa(
        context: Context?,
        name: String?,
        factory: SQLiteDatabase.CursorFactory?,
        version: Int
    ) : SQLiteOpenHelper(context, name, factory, version) {
        var sentencia =
            "CREATE TABLE alumno" +
                    "(" +
                    " nia integer NOT NULL," +
                    " nombre varchar(45) NOT NULL," +
                    " apellido1 varchar(45) NOT NULL," +
                    " apellido2 varchar(45)," +
                    " fecha_nacimiento date NOT NULL," +
                    " estado_matriculacion varchar(45) NOT NULL," +
                    " curso varchar(45) NOT NULL," +
                    " grupo varchar(45) NOT NULL," +
                    " incidencias varchar(250)," +
                    " pertenece_xarxa boolean NOT NULL," +
                    " PRIMARY KEY (nia)" +
                    ");"

        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            sqLiteDatabase.execSQL(sentencia)
        }

        override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i2: Int) {}
    }
}