@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.taskflow"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.taskflow"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // --> Dependencias añadidas para la interfaz TaskFlow:
    // CardView para el diseño de tarjetas
    implementation("androidx.cardview:cardview:1.0.0")
    // RecyclerView para la lista de tareas
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.fragment:fragment-ktx:1.8.9")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}