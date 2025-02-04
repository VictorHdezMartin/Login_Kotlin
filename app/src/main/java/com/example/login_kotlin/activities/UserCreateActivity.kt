package com.example.login_kotlin.activities

import android.app.DatePickerDialog
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.login_kotlin.databinding.ActivityUsercreateBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UserCreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUsercreateBinding

    private val FILE_REQUEST_CODE = 1001
 // Definir el ActivityResultLauncher para manejar el resultado del selector de archivos
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { loadImageIntoImageView(it) }
    }

    private val calendar = Calendar.getInstance()                                                   // nos hace falta para instanciar el DATAPICKER

    val minPassword = 6
    val maxPassword = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityUsercreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

     // limitamos el tamaño encaracteres de la contraseña
        binding.userPasswordCreate.counterMaxLength = maxPassword
        binding.userPasswordCreate.editText!!.filters = arrayOf(InputFilter.LengthFilter(maxPassword))

     // limitamos el tamaño de caracteres del repetir contraseña
        binding.userPasswordRepeat.counterMaxLength = maxPassword
        binding.userPasswordRepeat.editText!!.filters = arrayOf(InputFilter.LengthFilter(maxPassword))

        binding.datePicker.setOnClickListener {
            showDatePicker(binding.userNacimiento.editText)
        }

        binding.openExplorer.setOnClickListener {
            openFileExplorer()
        }

        binding.userHombre.setOnClickListener {

        }

        binding.userMujer.setOnClickListener {

        }

        binding.userNoProcede.setOnClickListener {

        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

// Función para abrir el explorador de archivos y elegir una imagen
    private fun openFileExplorer() {
        getContent.launch("image/*") // solo imagenes
}

// Metodo para cargar la imagen en el ImageView usando la URI
    private fun loadImageIntoImageView(uri: Uri) {
        try {
            val inputStream = contentResolver.openInputStream(uri)  // Obtener un InputStream de la URI
            val bitmap = BitmapFactory.decodeStream(inputStream)    // Decodificar la imagen a Bitmap
            binding.userImagen.setImageBitmap(bitmap)               // Establecer la imagen en el ImageView  <- cargamos la imagen
            inputStream?.close()                                    // Cerrar el InputStream
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        }
    }

// Funcion para generar un DATEPICKER desde código ------------------------------------------------
    private fun showDatePicker(vista: View?) {
        var hora: String
        val datePickerDialog = DatePickerDialog(
            this, { DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()                                           // Create a new Calendar instance to hold the selected date
                selectedDate.set(year, monthOfYear,dayOfMonth)                                      // Set the selected date using the values received from the DatePicker dialog
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())        // Create a SimpleDateFormat to format the date as "dd/MM/yyyy"
                val formattedDate = dateFormat.format(selectedDate.time)                            // Format the selected date into a string

                if (vista is EditText) {
                    vista as EditText
                    vista?.setText("$formattedDate")
                } else if (vista is TextView) {
                    vista as TextView
                    vista?.text = "$formattedDate"
                } else {
                    //error
                }
            },

            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()                                                                 // Show the DatePicker dialog
    }
}