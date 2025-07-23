plugins {
    alias(libs.plugins.clean.android.library)
    alias(libs.plugins.clean.jvm.ktor)
    //alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.apsone.core.data"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.timber)
    implementation(projects.core.domain)
    implementation(projects.core.database)
}