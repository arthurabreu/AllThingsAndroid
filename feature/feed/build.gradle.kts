plugins {
    id("allthingsandroid.android.feature")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.arthurabreu.allthingsandroid.feature.feed"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Room (paging source)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor3)

    // Ktor (reuse app's client via :core:data)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    testImplementation(libs.androidx.paging.runtime)
}
