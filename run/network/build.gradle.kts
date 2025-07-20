plugins {
    alias(libs.plugins.clean.android.library)
    alias(libs.plugins.clean.jvm.ktor)
}

android {
    namespace = "com.apsone.run.network"
}
dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}