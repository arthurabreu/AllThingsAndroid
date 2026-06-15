import org.gradle.api.Plugin
import org.gradle.api.Project

// Applies the Kover plugin to a module so it participates in coverage aggregation.
// Exclusions and verification gates are centralised in :app's kover {} block.
class AndroidKoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlinx.kover")
    }
}
