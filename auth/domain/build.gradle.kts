    plugins {
    alias(libs.plugins.clean.jvm.library)
}

dependencies {
    implementation(projects.core.domain)
}
