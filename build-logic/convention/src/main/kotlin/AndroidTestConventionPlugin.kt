import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            add("testImplementation", catalog.findLibrary("junit-jupiter").get())
            add("testRuntimeOnly", catalog.findLibrary("junit-jupiter-engine").get())
            add("testRuntimeOnly", catalog.findLibrary("junit-platform-launcher").get())
            add("testImplementation", catalog.findLibrary("mockk").get())
            add("testImplementation", catalog.findLibrary("kotlinx-coroutines-test").get())
            add("testImplementation", catalog.findLibrary("turbine").get())
            add("androidTestImplementation", catalog.findLibrary("mockk-android").get())
            add("androidTestImplementation", catalog.findLibrary("androidx-espresso-core").get())
        }
    }
}
