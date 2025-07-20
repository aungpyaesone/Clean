plugins {
    `kotlin-dsl`
}

group = "com.apsone.clean.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin{
    plugins {
        register("androidApplication"){
            id = "clean.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "clean.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary"){
            id = "clean.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose"){
            id = "clean.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureUi"){
            id = "clean.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

        register("androidRoom"){
            id = "clean.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("jvmLibrary"){
            id = "clean.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("jvmKtor"){
            id = "clean.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}
