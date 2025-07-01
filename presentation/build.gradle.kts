plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sargis.khlopuzyan.note.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.sargis.khlopuzyan.presentation.InstrumentationTestRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.domain)

    // Note NAVIGATION
    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // Additional icons
    implementation(libs.androidx.material.icons.extended.android)
//    implementation(libs.androidx.ui.tooling)

    // Injection Koin
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

    // Compose preview
    debugImplementation(libs.androidx.ui.tooling)



    // Local unit tests
    testImplementation(libs.androidx.core)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.truth)
    // In case we need to test api
//    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation(libs.mockk)
    testImplementation(libs.ui.test.manifest)


    // Instrumentation tests
    androidTestImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.core.ktx)
//    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    // This need to be included as no one knows why app crashes
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.koin.core)
    androidTestImplementation(libs.koin.test.junit4)

//
//
//    testImplementation(libs.junit)
//    testImplementation(libs.google.truth.v11)
//    // UI testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)

//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.google.truth.v11)
//    // Espresso for UI testing
    androidTestImplementation(libs.androidx.espresso.core)
//    // Used for some dependencies
//    debugImplementation(libs.androidx.ui.test.manifest)
}