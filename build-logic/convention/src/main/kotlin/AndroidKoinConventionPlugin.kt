import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidKoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        dependencies {
            val bom = catalog.findLibrary("koin-bom").get()
            add("implementation", platform(bom))
            add("implementation", catalog.findLibrary("koin-android").get())
            add("implementation", catalog.findLibrary("koin-compose").get())
        }
    }
}
