plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.profile"
}

dependencies {
    implementation(project(":commonscreens"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)
}
