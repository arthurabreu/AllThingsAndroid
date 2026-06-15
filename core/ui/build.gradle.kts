plugins {
    id("allthingsandroid.android.library")
    id("allthingsandroid.android.compose")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.core.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(libs.colorpicker.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
