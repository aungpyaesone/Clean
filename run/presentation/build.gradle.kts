plugins {
    alias(libs.plugins.clean.android.feature.ui)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.mapsplatform.secrets.plugin)
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
    implementation(libs.google.maps.android.compose)
    implementation(libs.androidx.activity.compose)
    implementation(libs.core.ktx)
    implementation(libs.timber)
    implementation(libs.coil.compose)
}