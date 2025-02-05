package com.example.login_kotlin.utils

import java.util.regex.Pattern

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"  // Expresión regular para validar el correo electrónico
    val pattern = Pattern.compile(emailRegex)
    return pattern.matcher(email).matches()
}

fun isValidPassword(password: String, minLength: Int, maxLength: Int): Boolean {
    return true

    val passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$"     // Expresión regular para validar la contraseña
    return password.length in minLength..maxLength && password.matches(passwordRegex.toRegex())  // Verificar longitud y patrón con regex
}