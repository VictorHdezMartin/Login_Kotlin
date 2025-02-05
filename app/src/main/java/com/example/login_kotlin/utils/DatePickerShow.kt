package com.example.login_kotlin.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.EditText
import androidx.activity.ComponentActivity
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

fun ComponentActivity.DatePickerShow(contexto: Context, fecha: EditText){

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
            fecha.textSize = 16F
            fecha.setText(formattedDate)
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