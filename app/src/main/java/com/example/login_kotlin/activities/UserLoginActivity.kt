package com.example.login_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUserloginBinding
import com.example.login_kotlin.utils.AlertDialogConstants
import com.example.login_kotlin.utils.isValidEmail
import com.example.login_kotlin.utils.isValidPassword
import com.example.login_kotlin.utils.showDialog

class UserLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserloginBinding

    val minPassword = 6
    val maxPassword = 20

    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUserloginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userPassword.counterMaxLength = maxPassword
        binding.userPassword.editText!!.filters = arrayOf(InputFilter.LengthFilter(maxPassword))

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

      //     contexto: Context, titulo: String, mensaje: String, btnOK: String?, btnFalse: String?) {

        with (binding) {
            CrearCuenta.setOnClickListener {
                goToCrearCuenta()
            }

            IniciarSesion.setOnClickListener {
                goToLogin()
            }

            RecordarPassword.setOnClickListener {

            }

            InfPassword.setOnClickListener {

            }

            LoginGoogle.setOnClickListener {

            }

            LoginOutlook.setOnClickListener {

            }

            LoginApple.setOnClickListener {

            }

            LoginFacebook.setOnClickListener {

            }

            userEmail.editText?.addTextChangedListener {
                IniciarSesion.isEnabled =  isValidEmail(userEmail.editText!!.text.toString())
            }

            userPassword.editText?.addTextChangedListener {
                IniciarSesion.isEnabled = isValidPassword(userPassword.editText!!.text.toString(), minPassword, maxPassword)
            }
        }
    }

// Vamos al activity de Crear cuenta de usuario  ---------------------------------------------------

    private fun goToCrearCuenta() {
        val intent = Intent(this, UserCreateActivity::class.java)
        startActivity(intent)
    }


    fun goToLogin() {
        if (isValidEmail(binding.userEmail.editText!!.text.toString()) && isValidPassword(binding.userPassword.editText!!.text.toString(), minPassword, maxPassword))

            showDialog(this@UserLoginActivity, "usuario login", "pasaríamos a logearnos por Firebase", "continuar", null, null) {
                when (it) {
                    AlertDialogConstants.BTN_OK -> {}
                    AlertDialogConstants.BTN_FALSE -> {}
                    AlertDialogConstants.BTN_NEUTRAL -> {}
                }
            }
        else {

            showDialog(this@UserLoginActivity, "usuario login", "ERROR", "continuar", null, null) {
                when (it) {
                    AlertDialogConstants.BTN_OK -> {}
                    AlertDialogConstants.BTN_FALSE -> {}
                    AlertDialogConstants.BTN_NEUTRAL -> {}
                }
            }

        }

    }








}


/*

   findViewById<TextInputLayout>(R.id.userPassword).error = "La contraseña debe tener ..... askldf josdh fklbsldh fbiab ds ifbjasdg fgaisd gfigiads gfi gadiysgfagd si f"



showDialog(this@UserLoginActivity, "Login Google", "Login Google", "OK", "salir", "neutro") {
    when (it) {
        AlertDialogConstants.BTN_OK -> {}
        AlertDialogConstants.BTN_FALSE -> {}
        AlertDialogConstants.BTN_NEUTRAL -> {}
    }
}*/
