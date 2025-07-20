plugins {
    alias(libs.plugins.clean.android.library)
}

android {
    namespace = "com.apsone.run.data"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.run.domain)
    implementation(libs.androidx.core.ktx)
}