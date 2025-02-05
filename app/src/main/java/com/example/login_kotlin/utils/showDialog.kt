package com.example.login_kotlin.utils

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AlertDialog

class AlertDialogConstants {
    companion object {
        const val BTN_OK = 0
        const val BTN_FALSE = 1
        const val BTN_NEUTRAL = 2
    }
}

fun ComponentActivity.DialogShow(contexto: Context, titulo: String, mensaje: String, btnOK: String?, btnFalse: String?, btnNeutro: String?, onButtonClick:(Int) -> Unit) {
    // Crear un builder de AlertDialog
    val builder = AlertDialog.Builder(contexto)

    builder.setTitle(titulo) // Título del diálogo
        .setMessage(mensaje) // Mensaje
        .setCancelable(false) // No se puede cancelar tocando fuera del diálogo

    if (btnOK != null) {
        builder.setPositiveButton(btnOK) { dialog, which ->
            onButtonClick(AlertDialogConstants.BTN_OK)
        }
    }

    if (btnFalse != null) {
        builder.setNegativeButton(btnFalse) { dialog, which ->
            onButtonClick(AlertDialogConstants.BTN_FALSE)
        }
    }

    if (btnFalse != null) {
        builder.setNeutralButton(btnFalse) { dialog, which ->
            onButtonClick(AlertDialogConstants.BTN_NEUTRAL)
        }
    }

    // Mostrar el diálogo
    val alertDialog = builder.create()
    alertDialog.show()
}