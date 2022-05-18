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
import java.security.MessageDigest

class LoginFragment : Fragment() {

    private lateinit var _binding: LayoutLoginBinding
    private val binding get() = _binding
    private lateinit var adaptadorAPIRest: APIRestAdapter
    private var usuarioLogin = Usuario()

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
            if (!binding.nombreUsuarioEditText.text.isNullOrEmpty() && !binding.contrasenyaEditText.text.isNullOrEmpty()) {
                getUsuario()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Debes introducir un usuario y contraseña para entrar al sistema",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        return view
    }

    private fun getUsuario() {
        CoroutineScope(Dispatchers.Main).launch {
            val nombreUsuario = binding.nombreUsuarioEditText.text
            val password = binding.contrasenyaEditText.text

            usuarioLogin.nombreUsuario = nombreUsuario.toString().lowercase()
            usuarioLogin.contrasenya = sha256Encrypt(password.toString())
            val response = adaptadorAPIRest.loginUsuarioAsync(usuarioLogin).await()
            if (response.isSuccessful) {
                binding.loginErrorTextView.visibility = View.GONE
                val sessionId = limpiarCookie(response.headers().get("Set-Cookie")!!)
                val usuario = adaptadorAPIRest.getUsuarioByNombreAsync(
                    nombreUsuario.toString().lowercase(),
                    sessionId
                ).await()
                val contrasenyaCorrecta = usuario.contrasenya == sha256Encrypt(password.toString())
                if (contrasenyaCorrecta) {
                    val intento = Intent(requireActivity(), AccionesActivity::class.java)
                    intento.putExtra("TIPO_USUARIO", usuario.tipoUsuario)
                    intento.putExtra("SESSION-ID", sessionId)
                    startActivity(intento)
                }
            } else {
                binding.loginErrorTextView.visibility = View.VISIBLE
            }
        }
    }


    private fun sha256Encrypt(password: String): String {
        return MessageDigest
            .getInstance("SHA-256")
            .digest(password.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }

    private fun limpiarCookie(cookie: String): String {
        return cookie.split(';')[0]
    }
}