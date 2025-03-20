plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    id("com.google.gms.google-services")
    // Add the Crashlytics Gradle plugin
    id("com.google.firebase.crashlytics")
}


android {
    namespace = "com.example.moodbloom"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.moodbloom"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
        viewBinding = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose BOM for managing Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.work.runtime.ktx)

    // AndroidX JUnit for instrumentation tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Compose BOM for test dependencies
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // Compose UI testing library
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Compose UI tooling for debugging and development
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Lifecycle components (redundant, but included for clarity)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI libraries
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Compose UI testing libraries (redundant, but included for clarity)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Compose UI preview and layout libraries
    implementation(libs.androidx.compose.preview)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.compose.foundation)

    // General AndroidX libraries
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // JUnit for unit tests
    testImplementation(libs.junit)

    // AndroidX JUnit and Espresso for instrumentation tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation components for managing app navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.dynamic)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.hilt.navigation)

    // Firebase libraries
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
   /* implementation(libs.play.services.auth)*/

    implementation(libs.gms.play.services.auth)

    // Hilt for dependency injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization)
    implementation (libs.gson)

    // coil for image loader
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    //lottie for animation
    implementation(libs.lottie.compose)
    //google fonts
    implementation (libs.androidx.ui.text.google.fonts)

    implementation (libs.ycharts)

    //DataStore
    implementation(libs.datastore.preferences)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

}