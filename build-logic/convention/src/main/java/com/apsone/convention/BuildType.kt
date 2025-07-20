package com.apsone.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.api.dsl.BuildType
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension< *, *, *, *, *, *>,
    extensionType: ExtensionType
){
    commonExtension.buildFeatures.buildConfig = true
    commonExtension.run {
       val apiKey = gradleLocalProperties(
            rootDir,
            providers = providers
        ).getProperty("API_KEY")
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(apiKey,commonExtension)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(apiKey,commonExtension)
                        }
                    }
                }
            }
        }
    }

}

private fun BuildType.configureDebugBuildType(apiKey: String){
    buildConfigField("String","API_KEY","\"$apiKey\"")
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType (apiKey: String,
                                                 commonExtension: CommonExtension<*, *, *, *, *, *>){
    buildConfigField("String","API_KEY","\"$apiKey\"")
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")
    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}


