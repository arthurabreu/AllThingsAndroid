plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.maps"
}

dependencies {
    implementation(project(":commonscreens"))

    implementation(libs.maps.compose)
    implementation(libs.maps.compose.utils)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)
}
