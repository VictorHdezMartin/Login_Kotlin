package com.example.login_kotlin.activities

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUserloginBinding
import com.example.login_kotlin.utils.DialogShow
import com.example.login_kotlin.utils.PasswordConstants
import com.example.login_kotlin.utils.isValidEmail
import com.example.login_kotlin.utils.isValidPassword
import com.example.login_kotlin.utils.showPasswordParametres
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserloginBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = Firebase.firestore                                                                     // Inicializar Firestore
        auth = FirebaseAuth.getInstance()                                                           // Inicializar Firebase Auth

        binding = ActivityUserloginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userPassword.counterMaxLength = PasswordConstants.MAX_PASSWORD
        binding.userPassword.editText!!.filters = arrayOf(InputFilter.LengthFilter(PasswordConstants.MAX_PASSWORD))

        binding.CrearCuenta.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#000000"))

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        with(binding) {
            CrearCuenta.setOnClickListener {
                goToCrearCuenta()
            }

            RecordarPassword.setOnClickListener {
              goToRecordarPassword()
            }

            InfPassword.setOnClickListener {
                showPasswordParametres(this@UserLoginActivity)
            }

            IniciarSesion.setOnClickListener {
                signInWithFireBase()
            }

            LoginGoogle.setOnClickListener {
                signInWithGoogle()
            }

            LoginOutlook.setOnClickListener {
                signInWithOutLook()
            }

            LoginApple.setOnClickListener {
                signInWithApple()
            }

            LoginFacebook.setOnClickListener {
                signInWithFaceBook()
            }

            userEmail.editText?.addTextChangedListener {
                enabledLogin()
            }

            userPassword.editText?.addTextChangedListener {
                enabledLogin()
            }
        }
    }

// Habilitar boton Login
   fun enabledLogin() {
       with(binding) {
           IniciarSesion.isEnabled = isValidEmail(userEmail.editText!!.text.toString()) && isValidPassword(userPassword.editText!!.text.toString())

            if (IniciarSesion.isEnabled)
                binding.IniciarSesion.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#000000"))
            else
                IniciarSesion.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F4F3F3"))
        }
   }

// Vamos al activity de Crear cuenta de usuario  ---------------------------------------------------
    private fun goToCrearCuenta() {
        val intent = Intent(this, UserCreateActivity::class.java)
        startActivity(intent)
    }

// Recordar Password  ------------------------------------------------------------------------------
   fun goToRecordarPassword(){
 //   startActivity(Intent(this, ResetPasswordActivity::class.java))
   }

// Login por Firebase  -----------------------------------------------------------------------------
    fun signInWithFireBase() {
        if (isValidEmail(binding.userEmail.editText!!.text.toString()) &&
            isValidPassword(binding.userPassword.editText!!.text.toString())) {

            val email = binding.userEmail.editText!!.text.toString().trim()
            val password = binding.userPassword.editText!!.text.toString().trim()
            loginWithEmailPassword(email, password)
        } else {
            DialogShow(this@UserLoginActivity,
                       "Login Firebase:",
                       "Recuerda introducir un correo electrónico válido y contraseña según requisitos, para más información pulsa el botón (?)",
                       "Continuar",
                       null,
                       null) {}
        }
    }

    private fun loginWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToAPP()
                } else {
                    DialogShow(
                        this@UserLoginActivity,
                        "Login Firebase:",
                        "Usuario no registrado",
                        "Continuar",
                        null,
                        null
                    ) {}
                }
            }
    }

// Login con Google  -------------------------------------------------------------------------------
    private fun signInWithGoogle() {

    }

// Login con Apple  -------------------------------------------------------------------------------
    fun signInWithApple() {

    }

// Login con OutLook  -------------------------------------------------------------------------------
    fun signInWithFaceBook() {

    }

// Login con OutLook  -------------------------------------------------------------------------------
    private fun signInWithOutLook() {

    }

// VAMOS A NUESTRA APP  ----------------------------------------------------------------------------
   private fun goToAPP() {

    Toast.makeText(this, "VAMOS A NUESTRA APP", Toast.LENGTH_SHORT)
         .show()     // Login exitoso ( ELIMINAR )
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
