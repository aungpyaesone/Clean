plugins {
    alias(libs.plugins.clean.android.library)
    alias(libs.plugins.clean.android.room)
}

android {
    namespace = "com.apsone.core.database"

}

dependencies {
    implementation(libs.org.mongodb.bson)
    implementation(projects.core.domain)
    implementation(libs.bundles.koin)
}