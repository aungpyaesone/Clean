plugins {
    alias(libs.plugins.clean.android.feature.ui)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.apsone.auth.presentation"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
    implementation(libs.androidx.foundation.android)
    implementation(libs.core.ktx)
}

