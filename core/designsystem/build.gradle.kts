plugins {
    id("allthingsandroid.android.library")
    id("allthingsandroid.android.compose")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.core.designsystem"
}

dependencies {
    implementation(libs.androidx.material.icons.extended)
}
