plugins {
    id("allthingsandroid.android.library")
    id("allthingsandroid.android.koin")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.core.common"
}

dependencies {
    implementation(project(":core:model"))
}
