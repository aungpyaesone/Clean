package com.apsone.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*,*,*,*,*,*>
){
    commonExtension.run {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
        }

        lint {
            baseline = file("lint-baseline.xml")
            disable.add("CompositionLocalNaming")
            disable.add("UnrememberedMutableState")
            disable.add("FlowOperatorInvokedInComposition")
            disable.add("ComposableNaming")
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
        }
    }
}