plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.settings"
}

dependencies {
    implementation(project(":commonscreens"))

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.datastore.preferences)
}
