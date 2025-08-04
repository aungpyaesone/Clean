import org.jetbrains.kotlin.js.inline.util.getSimpleName

plugins {
    alias(libs.plugins.clean.android.library)
}

android {
    namespace = "com.apsone.run.location"
}

dependencies {
    implementation(libs.bundles.koin)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.google.android.gms.play.services.location)
    implementation(projects.core.domain)
    implementation(projects.run.domain)
}