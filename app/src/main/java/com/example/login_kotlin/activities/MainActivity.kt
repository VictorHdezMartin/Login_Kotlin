package com.example.login_kotlin.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityMainBinding
import com.example.login_kotlin.utils.AlertDialogConstants
import com.example.login_kotlin.utils.showDialog

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val btnOK = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

      //     contexto: Context, titulo: String, mensaje: String, btnOK: String?, btnFalse: String?) {

            with (binding) {
                CrearCuenta.setOnClickListener {
                    showDialog(this, "Crear cuenta", "crear cuenta", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                IniciarSesion.setOnClickListener {
                    showDialog(this, "Iniciar sesión", "iniciar sesion", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                RecordarPassword.setOnClickListener {
                    showDialog(this, "Recordar contraseña", "Recordar contraseña", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                InfPassword.setOnClickListener {
                    showDialog(this, "Informacion Password", "Informacion Password", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                LoginGoogle.setOnClickListener {
                    showDialog(this, "Login Google", "Login Google", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                LoginOutlook.setOnClickListener {
                    showDialog(this, "Login Outlook", "Login Outlook", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                LoginApple.setOnClickListener {
                    showDialog(this, "Login Apple", "iLogin Apple", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                LoginFacebook.setOnClickListener {
                    showDialog(this, "Login Facebook", "Login Facebook", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                userEmail.editText?.addTextChangedListener {
                    showDialog(this, "User email", "user email", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
                userPassword.editText?.addTextChangedListener {
                    showDialog(this, "User Password", "user password", "OK", "salir", "neutro") {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
            }






     //   findViewById<TextInputLayout>(R.id.userPassword).error = "La contraseña debe tener ..... askldf josdh fklbsldh fbiab ds ifbjasdg fgaisd gfigiads gfi gadiysgfagd si f"

    }





}