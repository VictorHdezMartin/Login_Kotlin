import io.grpc.internal.SharedResourceHolder.release

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services")  // Para integrar Firebase
}

android {
    namespace = "com.example.login_kotlin"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.login_kotlin"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

// dependencias de FireBase
    implementation("com.google.firebase:firebase-bom:33.8.0")            // Import the BoM for the Firebase platform
    implementation("com.google.firebase:firebase-core:21.1.0")           // Firebase Core (Obligatorio para usar Firebase)

    implementation("com.google.firebase:firebase-analytics-ktx:21.0.0")  // Firebase Analytics
 //   implementation("com.google.firebase:firebase-analytics-ktx")         // Firebase Analytics

    implementation("com.google.firebase:firebase-auth-ktx:21.1.0")       // Firebase Authentication
    implementation("com.google.firebase:firebase-firestore-ktx:24.6.0")  // Firebase Firestore

// dependencias para Google
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("androidx.credentials:credentials:1.0.0")

// dependencias de Google Sign-In (para integración con Google)
    implementation("com.google.android.gms:play-services-auth:21.3.0")   // Google Sign-In (para integración con Google)
    implementation("com.google.firebase:firebase-auth:21.1.0")           // Firebase Authentication (Google Sign-In requiere esta dependencia)

// Kotlin Reflexion
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24")

// dependencia filament
    implementation(libs.filament.android)
    implementation(libs.places)
    implementation(libs.googleid)        // hay que copiarlo fichero: 'libs.versions.toml' -> filament-android = { group = "com.google.ar.sceneform", name = "filament-android", version.ref = "filamentAndroid" }
                                       // para trabajar con gráficos 3D en Android

// dependencias RETROFIT
    val retroFitVersion = "2.11.0"
    implementation("com.squareup.retrofit2:retrofit:$retroFitVersion")                              // RetroFit
    implementation("com.squareup.retrofit2:converter-gson:$retroFitVersion")                        // Gson

// dependencia PICASSO
    implementation("com.squareup.picasso:picasso:2.8")                                              // Picasso

// dependencia para las notificaciones
//    implementation("androidx.core:core:2.2.0")

// dependencias sqLITE
    implementation(libs.androidx.sqlite)           //  hay que copiarlo fichero: 'libs.versions.toml' -> androidx-sqlite = { group = "androidx.sqlite", name = "sqlite", version.ref = "sqlite" }

// dependencia material3
    implementation(libs.androidx.material3.android)

// dependencia i18n
    implementation(libs.androidx.core.i18n)

// Propias de ANDROID
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}