package com.example.login_kotlin.activities

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUsercreateBinding
import com.example.login_kotlin.utils.DatePickerShow
import com.example.login_kotlin.utils.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsercreateBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    // private val FILE_REQUEST_CODE = 1001                            // para poder acceder a ficheros

    // Definir el ActivityResultLauncher para manejar el resultado del selector de archivos
    ///   private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
    //       uri?.let { loadImageIntoImageView(it) }
    //   }

    private val maxPassword = 20
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
            userPasswordCreate.counterMaxLength = maxPassword
            userPasswordCreate.editText!!.filters =
                arrayOf(InputFilter.LengthFilter(maxPassword))

            // limitamos el tamaño de caracteres del repetir contraseña
            userPasswordRepeat.counterMaxLength = maxPassword
            userPasswordRepeat.editText!!.filters =
                arrayOf(InputFilter.LengthFilter(maxPassword))

            userNacimiento.editText!!.textSize = 12F

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
