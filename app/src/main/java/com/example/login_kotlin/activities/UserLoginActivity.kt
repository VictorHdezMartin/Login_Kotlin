package com.example.login_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.login_kotlin.databinding.ActivityUserloginBinding
import com.example.login_kotlin.utils.DialogShow
import com.example.login_kotlin.utils.GoogleSingIn
import com.example.login_kotlin.utils.isValidEmail
import com.example.login_kotlin.utils.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserloginBinding
    private lateinit var googleSingIn: GoogleSingIn

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    val minPassword = 6
    val maxPassword = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = Firebase.firestore                                                                     // Inicializar Firestore
        auth = FirebaseAuth.getInstance()                                                           // Inicializar Firebase Auth
        googleSingIn = GoogleSingIn(this)


        binding = ActivityUserloginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userPassword.counterMaxLength = maxPassword
        binding.userPassword.editText!!.filters = arrayOf(InputFilter.LengthFilter(maxPassword))

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
      //          startActivity(Intent(this, ResetPasswordActivity::class.java))
             //   goToRecordarPassword()

            }

            InfPassword.setOnClickListener {

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
                IniciarSesion.isEnabled = isValidEmail(userEmail.editText!!.text.toString())
            }

            userPassword.editText?.addTextChangedListener {
                IniciarSesion.isEnabled = isValidPassword(
                    userPassword.editText!!.text.toString(),
                    minPassword,
                    maxPassword
                )
            }
        }
    }

// Vamos al activity de Crear cuenta de usuario  ---------------------------------------------------
    private fun goToCrearCuenta() {
        val intent = Intent(this, UserCreateActivity::class.java)
        startActivity(intent)
    }

// Recordar Password  ------------------------------------------------------------------------------
   fun goToRecordarPassword(){


   }

// Login por Firebase  -----------------------------------------------------------------------------
    fun signInWithFireBase() {
        if (isValidEmail(binding.userEmail.editText!!.text.toString()) &&
            isValidPassword(binding.userPassword.editText!!.text.toString(),minPassword,maxPassword)
        ) {
            val email = binding.userEmail.editText!!.text.toString().trim()
            val password = binding.userPassword.editText!!.text.toString().trim()
            loginWithEmailPassword(email, password)
        } else {
            DialogShow(this@UserLoginActivity,
                       "Login Firebase:",
                       "Recuerda introducir correo electrónico y contraseña",
                       "Continuar",
                       null,
                       null) {}
        }
    }

    private fun loginWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "VAMOS A NUESTRA APP", Toast.LENGTH_SHORT)
                        .show()     // Login exitoso ( ELIMINAR )

                    // NOS VAMOS A NUESTRA APP

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
        CoroutineScope(Dispatchers.Main).launch {
            if (googleSingIn.signIn()) {
                val currentUser = FirebaseAuth.getInstance().currentUser!!
                val db = FirebaseFirestore.getInstance()
                val userCollection = db.collection("users")
                userCollection.whereEqualTo("email", currentUser.email).get()
                    .addOnSuccessListener { }
            }
        }
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
