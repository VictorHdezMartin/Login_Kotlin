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
import com.example.login_kotlin.R
import com.example.login_kotlin.databinding.ActivityUsercreateBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class UserCreateActivity : AppCompatActivity() {

    lateinit var binding: ActivityUsercreateBinding

    private val FILE_REQUEST_CODE = 1001                            // para poder acceder a ficheros

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

        with (binding) {

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
                ShowDatePicker()
            }

            openExplorer.setOnClickListener {
                openFileExplorer()
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

// Función para abrir el explorador de archivos y elegir una imagen  -------------------------------
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

// Mostrar el DatePicker ---------------------------------------------------------------------------

    private fun ShowDatePicker() {

     // Obtén la fecha actual y calcula la fecha mínima (18 años atrás)
        val minAgeDate = Calendar.getInstance() // Fecha mínima: 18 años atrás
        minAgeDate.add(Calendar.YEAR , -18 )

     // Obtén el año, mes y día de la fecha mínima
        val year = minAgeDate.get(Calendar.YEAR)
        val month = minAgeDate.get(Calendar.MONTH)
        val dayOfMonth = minAgeDate.get(Calendar.DAY_OF_MONTH)

     // Mostrar DatePickerDialog con fecha mínima de 18 años
        val datePickerDialog = DatePickerDialog(
            this, { _, selectedYear, selectedMonth, selectedDay ->

             // Acción cuando se selecciona una fecha
                val selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)

            // Formatear la fecha en un formato legible (ejemplo: "dd/MM/yyyy")
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val formattedDate = selectedDate.format(formatter)

            // Pasar la fecha al EditText
                binding.userNacimiento.editText!!.textSize = 16F
                binding.userNacimiento.editText!!.setText(formattedDate)
            },
            year, month, dayOfMonth
        )

     // Limitar la selección a una fecha no posterior a la actual
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        datePickerDialog.datePicker.maxDate = minAgeDate.timeInMillis

     // Mostrar el DatePickerDialog
        datePickerDialog.show()
    }
}