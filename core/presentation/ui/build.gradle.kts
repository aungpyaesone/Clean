plugins {
    alias(libs.plugins.clean.android.library.compose)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.apsone.core.presentation.ui"
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    implementation(projects.core.domain)
    implementation(projects.core.presentation.designsystem)
    implementation(libs.core.ktx)
}