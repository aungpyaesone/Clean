import com.android.build.api.dsl.ApplicationExtension
import com.apsone.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project


class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("clean.android.application")
            pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

            val extension = target.extensions.getByType(ApplicationExtension::class.java)
            configureAndroidCompose(extension)

        }
    }
}