plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
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

// dependencia filament
    implementation(libs.filament.android)        // hay que copiarlo fichero: 'libs.versions.toml' -> filament-android = { group = "com.google.ar.sceneform", name = "filament-android", version.ref = "filamentAndroid" }
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