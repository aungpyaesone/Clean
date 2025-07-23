import com.android.build.api.dsl.LibraryExtension
import com.apsone.convention.configureAndroidCompose
import com.apsone.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }
            /*extensions.configure<KotlinAndroidProjectExtension> {
                jvmToolchain(17)
            }*/

            val extension = extensions.getByType(LibraryExtension::class.java)
            configureAndroidCompose(extension)
        }
    }
}