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
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUserloginBinding
import com.example.login_kotlin.utils.AlertDialogConstants
import com.example.login_kotlin.utils.DialogShow
import com.example.login_kotlin.utils.isValidEmail
import com.example.login_kotlin.utils.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider


class UserLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserloginBinding

    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    val minPassword = 6
    val maxPassword = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = Firebase.firestore                                           // Inicializar Firestore
        auth = FirebaseAuth.getInstance()                               // Inicializar Firebase Auth
        googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)  // Inicializar Google Sign-In

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

        with(binding) {
            CrearCuenta.setOnClickListener {
                goToCrearCuenta()
            }

            RecordarPassword.setOnClickListener {

            }

            InfPassword.setOnClickListener {

            }

            IniciarSesion.setOnClickListener {
                goToLogin()
            }

            LoginGoogle.setOnClickListener {
                signInWithGoogle()
            }

            LoginOutlook.setOnClickListener {

            }

            LoginApple.setOnClickListener {

            }

            LoginFacebook.setOnClickListener {

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

// Login por Firebase  -----------------------------------------------------------------------------
    fun goToLogin() {
        if (isValidEmail(binding.userEmail.editText!!.text.toString()) &&
            isValidPassword(binding.userPassword.editText!!.text.toString(),minPassword,maxPassword)
        ) {
            val email = binding.userEmail.editText!!.text.toString().trim()
            val password = binding.userPassword.editText!!.text.toString().trim()

            loginWithEmailPassword(email, password)
        } else {
            Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginWithEmailPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "VAMOS A NUESTRA APP", Toast.LENGTH_SHORT)
                        .show()     // Login exitoso

                    // NOS VAMOS A NUESTRA APP
                } else {
                    DialogShow(
                        this@UserLoginActivity,
                        "Login Firebase",
                        "Error: usuario no regitrado",
                        "Continuar",
                        null,
                        null
                    ) {
                        when (it) {
                            AlertDialogConstants.BTN_OK -> {}
                            AlertDialogConstants.BTN_FALSE -> {}
                            AlertDialogConstants.BTN_NEUTRAL -> {}
                        }
                    }
                }
            }
    }

// Login con Google  -------------------------------------------------------------------------------

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

     // Result of Google Sign-In
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
             // El usuario ha iniciado sesión correctamente
                val account = task.result
                firebaseAuthWithGoogle(account!!)
            } else {
                task.exception?.printStackTrace()
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                 // Login exitoso
                    Toast.makeText(this, "VAMOS A NUESTRA APP", Toast.LENGTH_SHORT).show()

                    // VAMOS A NUESTRA APP

                } else {
                    // Error en la autenticación
                    Toast.makeText(this, "Error con Firebase Authentication", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }


}  // ultima llave

/*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = Firebase.auth
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        val signInButton: Button = findViewById(R.id.signInButton)
        signInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(account.idToken))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            // Usuario autenticado correctamente
                        } else {
                            // Manejar error de autenticación
                        }
                    }
            } catch (e: ApiException) {
                // Manejar error de autenticación
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}*/









/*

   findViewById<TextInputLayout>(R.id.userPassword).error = "La contraseña debe tener ..... askldf josdh fklbsldh fbiab ds ifbjasdg fgaisd gfigiads gfi gadiysgfagd si f"



showDialog(this@UserLoginActivity, "Login Google", "Login Google", "OK", "salir", "neutro") {
    when (it) {
        AlertDialogConstants.BTN_OK -> {}
        AlertDialogConstants.BTN_FALSE -> {}
        AlertDialogConstants.BTN_NEUTRAL -> {}
    }
}*/
