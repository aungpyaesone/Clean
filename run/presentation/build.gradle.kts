plugins {
    alias(libs.plugins.clean.android.feature.ui)
}

android {
    namespace = "com.apsone.run.presentation"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.run.domain)
    implementation(libs.androidx.core.ktx)
}