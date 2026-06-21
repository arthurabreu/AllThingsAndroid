plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.clouddb"
}

dependencies {
    implementation(project(":commonscreens"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)
    implementation(libs.kotlinx.coroutines.play.services)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)
}
