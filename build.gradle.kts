// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false

    // Aplicamos el plugin de Google Services
    id("com.google.gms.google-services") version "4.3.15"      // Usa la versión más reciente
}

// build.gradle (Project)
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.15") // Asegúrate de usar la versión más reciente
    }
}
