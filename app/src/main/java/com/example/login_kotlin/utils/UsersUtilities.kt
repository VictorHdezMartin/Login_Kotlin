package com.example.login_kotlin.utils

import android.content.Context
import androidx.activity.ComponentActivity
import java.util.regex.Pattern

class PasswordConstants {
    companion object {
        const val MIN_PASSWORD = 8
        const val MAX_PASSWORD = 20
    }
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"  // Expresión regular para validar el correo electrónico
    val pattern = Pattern.compile(emailRegex)
    return pattern.matcher(email).matches()
}

fun isValidPassword(password: String): Boolean {
    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{${PasswordConstants.MIN_PASSWORD},${PasswordConstants.MAX_PASSWORD}}$"     // Expresión regular para validar la contraseña
    return (password.length in PasswordConstants.MIN_PASSWORD..PasswordConstants.MAX_PASSWORD) &&
            (password.matches(passwordRegex.toRegex()))
}

fun ComponentActivity.showPasswordParametres(contexto: Context) {
    DialogShow(contexto,
              "# Contraseña (requisitos):",
              "Al menos un carácter en minúculas de la a..z debe estar presente\n\n"+
                      "Al menos un carácter en mayúscula de la A..la Z debe estar presente\n\n" +
                      "Al menos un número del 0..9 debe de estar presente\n\n" +
                      "Debe de contener al menos uno de los siguientes caracteres: @ \\ $ ! % * ? & \n\n" +
                      "La longitud de la contraseña debe de estar entre 8 y 20 caracteres",
              "Continuar",
              null,
              null ) {}
}