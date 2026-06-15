plugins {
    id("allthingsandroid.android.library")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.core.testing"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))

    api(libs.junit.jupiter)
    api(libs.junit.jupiter.engine)
    api(libs.junit.platform.launcher)
    api(libs.mockk)
    api(libs.kotlinx.coroutines.test)
    api(libs.turbine)
    api(libs.androidx.core.testing)
    api(libs.robolectric)
}
