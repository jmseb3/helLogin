import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.compose.ComposePlugin
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class HelloginComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "org.jetbrains.compose")

            val composeDeps = ComposePlugin.Dependencies(target)
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(composeDeps.runtime)
                    implementation(composeDeps.foundation)
                    implementation(composeDeps.material3)
                    implementation(composeDeps.components.resources)
                    implementation(composeDeps.components.uiToolingPreview)
                }
                sourceSets.commonTest.dependencies {
                    @OptIn(ExperimentalComposeLibrary::class)
                    implementation(composeDeps.uiTest)
                }
            }
        }
    }
}