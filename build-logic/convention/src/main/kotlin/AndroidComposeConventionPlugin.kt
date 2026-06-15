import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        pluginManager.withPlugin("com.android.application") {
            extensions.configure(ApplicationExtension::class.java) {
                buildFeatures { compose = true }
            }
        }
        pluginManager.withPlugin("com.android.library") {
            extensions.configure(LibraryExtension::class.java) {
                buildFeatures { compose = true }
            }
        }

        dependencies {
            val bom = catalog.findLibrary("androidx-compose-bom").get()
            add("implementation", platform(bom))
            add("implementation", catalog.findLibrary("androidx-ui").get())
            add("implementation", catalog.findLibrary("androidx-ui-graphics").get())
            add("implementation", catalog.findLibrary("androidx-ui-tooling-preview").get())
            add("implementation", catalog.findLibrary("androidx-material3").get())
            add("debugImplementation", catalog.findLibrary("androidx-ui-tooling").get())
            add("androidTestImplementation", platform(bom))
        }
    }
}
