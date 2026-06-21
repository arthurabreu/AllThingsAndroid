plugins {
    id("allthingsandroid.android.feature")
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.notifications"
}

dependencies {
    implementation(project(":commonscreens"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.datastore.preferences)
}
