import com.android.build.api.dsl.LibraryExtension
import com.apsone.convention.addUiLayerDependencies
import com.apsone.convention.configureAndroidCompose
import com.apsone.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureUiConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply("clean.android.library.compose")
            }

            dependencies {
                addUiLayerDependencies(target)
            }
        }
    }
}