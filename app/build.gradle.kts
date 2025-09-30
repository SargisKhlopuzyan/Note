import org.gradle.kotlin.dsl.androidTest

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.sargis.khlopuzyan.note"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.sargis.khlopuzyan.note"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.sargis.khlopuzyan.data.InstrumentationTestRunner"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
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
        buildConfig = true
//        compose = true
    }
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)

    implementation(libs.androidx.core.ktx)

    // Injection Koin
    implementation(libs.koin.android)

//    implementation(libs.androidx.activity.compose)
//    implementation(libs.androidx.runner)


//    androidTestImplementation(group = "app:data", name = "data",  configuration = "androidTestImplementation")
    androidTestImplementation(projects.data)
    androidTestImplementation(projects.presentation)


    // Test
    testImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.junit)


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