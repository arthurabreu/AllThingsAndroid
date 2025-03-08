plugins {
    id("com.android.library")
    kotlin("android")
}

android {
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
}

dependencies {
    implementation(commonscreenslibs.androidx.compose.bom.v20250200)
    implementation(commonscreenslibs.ui)
    implementation(commonscreenslibs.material3)
    implementation(commonscreenslibs.ui.tooling.preview)
    debugImplementation(commonscreenslibs.ui.tooling)
}