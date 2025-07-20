plugins {
    alias(libs.plugins.clean.android.feature.ui)
}

android {
    namespace = "com.apsone.auth.presentation"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(projects.core.domain)
    implementation(projects.auth.domain)
}
configurations.all {
    resolutionStrategy {
        force("androidx.compose.foundation:foundation:1.6.7")
    }
}

