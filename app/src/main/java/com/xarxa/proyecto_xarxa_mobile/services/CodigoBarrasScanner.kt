package com.xarxa.proyecto_xarxa_mobile.services

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class CodigoBarrasScanner : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private lateinit var escanerZXing: ZXingScannerView

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        escanerZXing = ZXingScannerView(this)
        setContentView(escanerZXing)
    }

    override fun onResume() {
        super.onResume()
        escanerZXing.setResultHandler(this)
        escanerZXing.startCamera()
    }

    override fun onPause() {
        super.onPause()
        escanerZXing.stopCamera() // Pausar en onPause
    }

    override fun handleResult(resultado: Result?) {
        val codigo: String = resultado!!.text
        val intentRegreso = Intent()
        intentRegreso.putExtra("codigo", codigo)
        setResult(Activity.RESULT_OK, intentRegreso)
        finish()
    }
}