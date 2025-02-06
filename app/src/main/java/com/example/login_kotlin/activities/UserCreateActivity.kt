package com.example.login_kotlin.activities

import android.os.Bundle
import android.text.InputFilter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUsercreateBinding
import com.example.login_kotlin.utils.DatePickerShow
import com.example.login_kotlin.utils.ImagePicker
import com.example.login_kotlin.utils.PasswordConstants
import com.example.login_kotlin.utils.showPasswordParametres
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsercreateBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private lateinit var imagePicker: ImagePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        db = Firebase.firestore                                         // Inicializar Firestore
        auth = FirebaseAuth.getInstance()                               // Inicializar Firebase Auth

        binding = ActivityUsercreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagePicker = ImagePicker(this) { uri -> binding.userImagen.setImageURI(uri) }

        with(binding) {

         // limitamos el tamaño encaracteres de la contraseña
            userPasswordCreate.counterMaxLength = PasswordConstants.MAX_PASSWORD
            userPasswordCreate.editText!!.filters = arrayOf(InputFilter.LengthFilter(PasswordConstants.MAX_PASSWORD))

         // limitamos el tamaño de caracteres del repetir contraseña
            userPasswordRepeat.counterMaxLength = PasswordConstants.MAX_PASSWORD
            userPasswordRepeat.editText!!.filters = arrayOf(InputFilter.LengthFilter(PasswordConstants.MAX_PASSWORD))

            userNacimiento.editText!!.textSize = 12F

            InfPassword1.setOnClickListener {
                showPasswordParametres(this@UserCreateActivity)
            }

            InfPassword2.setOnClickListener {
                showPasswordParametres(this@UserCreateActivity)
            }

            datePicker.setOnClickListener {
                DatePickerShow(this@UserCreateActivity, userNacimiento.editText!!)
            }

            openExplorer.setOnClickListener {
                imagePicker.openImagePicker()
            }

            userGenero.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.userHombre -> {}
                    R.id.userMujer -> {}
                    R.id.userNoProcede -> {}
                }
            }

            CrearCuenta.setOnClickListener {
                userCrearCuenta()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

// Creamos cuenta de usuario en FireBase  ---------------------------------------------------------
    private fun userCrearCuenta() {
        val genero = when (binding.userGenero.checkedRadioButtonId) {
            R.id.userHombre -> 0
            R.id.userMujer -> 1
            else -> 2
        }
    }

}
