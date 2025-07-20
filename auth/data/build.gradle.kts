plugins {
    alias(libs.plugins.clean.android.library)
    alias(libs.plugins.clean.jvm.ktor)
}

android {
    namespace = "com.apsone.auth.data"
}

dependencies {
    implementation(projects.auth.domain)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.bundles.koin)
}