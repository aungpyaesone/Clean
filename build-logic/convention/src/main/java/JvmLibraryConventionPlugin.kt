import com.android.build.api.dsl.LibraryExtension
import com.apsone.convention.ExtensionType
import com.apsone.convention.configureBuildTypes
import com.apsone.convention.configureKotlinAndroid
import com.apsone.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin


class JvmLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")
            configureKotlinJvm()
        }
    }
}