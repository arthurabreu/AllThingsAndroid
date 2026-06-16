plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.player"
}

dependencies {
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.exoplayer.hls)
    implementation(libs.media3.ui.compose)
    implementation(libs.media3.session)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)
}
