package com.xarxa.proyecto_xarxa_mobile.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xarxa.proyecto_xarxa_mobile.activities.AccionesActivity
import com.xarxa.proyecto_xarxa_mobile.databinding.LayoutLoginBinding
import com.xarxa.proyecto_xarxa_mobile.modelos.Usuario
import com.xarxa.proyecto_xarxa_mobile.services.APIRestAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.security.MessageDigest

class LoginFragment : Fragment() {

    private lateinit var _binding: LayoutLoginBinding
    private val binding get() = _binding
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private var usuario = Usuario()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = LayoutLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        adaptadorAPIRest = APIRestAdapter()

        binding.iniciarSesionButton.setOnClickListener {
            val intento = Intent(requireActivity(), AccionesActivity::class.java)
            startActivity(intento)
            /*
            if (!binding.nombreUsuarioEditText.text.isNullOrEmpty() && !binding.contrasenyaEditText.text.isNullOrEmpty()) {
                getUsuario()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Debes introducir un usuario y contraseña para entrar al sistema",
                    Toast.LENGTH_LONG
                ).show()
            }

             */
        }
        return view
    }

    private fun getUsuario() {
        CoroutineScope(Dispatchers.Main).launch {
            val nombreUsuario = binding.nombreUsuarioEditText.text
            val password = binding.contrasenyaEditText.text
            usuario = adaptadorAPIRest.getUsuarioByNombreAsync(nombreUsuario.toString()).await()
            val contrasenyaCorrecta = usuario.contrasenya == SHA256Encrypt(password.toString())
            if (contrasenyaCorrecta) {
                val intento = Intent(requireActivity(), AccionesActivity::class.java)
                startActivity(intento)
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Usuario o contraseña incorrectos. Inténtalo de nuevo",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun SHA256Encrypt(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val messageDigest = md.digest(password.toByteArray())
        val no = BigInteger(1, messageDigest)
        var hashtext = no.toString(16)
        while (hashtext.length < 32) {
            hashtext = "0$hashtext"
        }
        return hashtext
    }
}