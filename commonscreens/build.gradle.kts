plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.arthurabreu.allthingsandroid.commonscreens"
    compileSdk = 35

    defaultConfig {
        minSdk = 30
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2" // adjust as needed
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Add this block to set Kotlin's JVM target
    kotlinOptions {
        jvmTarget = "11"
    }
}


dependencies {
    implementation(commonscreenslibs.androidx.core.ktx)
    implementation(commonscreenslibs.androidx.lifecycle.viewmodel.compose)
    implementation(commonscreenslibs.androidx.navigation.compose)
    implementation(commonscreenslibs.androidx.lifecycle.runtime.ktx)
    implementation(commonscreenslibs.androidx.activity.compose)
    implementation(platform(commonscreenslibs.androidx.compose.bom))
    implementation(commonscreenslibs.androidx.ui)
    implementation(commonscreenslibs.androidx.ui.graphics)
    implementation(commonscreenslibs.androidx.ui.tooling)
    implementation(commonscreenslibs.androidx.ui.tooling.preview)
    implementation(commonscreenslibs.androidx.material3)
    implementation(libs.koin.compose)

    // ColorPicker
    implementation(commonscreenslibs.colorpicker.compose)
}