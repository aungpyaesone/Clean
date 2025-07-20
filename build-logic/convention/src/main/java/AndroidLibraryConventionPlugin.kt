import com.android.build.api.dsl.LibraryExtension
import com.apsone.convention.ExtensionType
import com.apsone.convention.configureBuildTypes
import com.apsone.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin


class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")

        val extension = extensions.getByType(LibraryExtension::class.java)

        configureKotlinAndroid(extension)
        configureBuildTypes(extension, ExtensionType.LIBRARY)

        extension.apply {
            defaultConfig {
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }
        }

        dependencies {
            "testImplementation"(kotlin("test"))
        }
    }
}
