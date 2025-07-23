plugins {
    alias(libs.plugins.clean.android.feature.ui)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.apsone.run.presentation"
    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.androidx.core.ktx)
    implementation(libs.core.ktx)
}