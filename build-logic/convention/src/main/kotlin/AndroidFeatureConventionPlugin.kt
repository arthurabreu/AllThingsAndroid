import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("allthingsandroid.android.library")
            apply("allthingsandroid.android.compose")
            apply("allthingsandroid.android.koin")
        }
        dependencies {
            add("implementation", project(":core:common"))
            add("implementation", project(":core:model"))
            add("implementation", project(":core:designsystem"))
            add("implementation", project(":core:ui"))
            add("testImplementation", project(":core:testing"))
        }
    }
}
