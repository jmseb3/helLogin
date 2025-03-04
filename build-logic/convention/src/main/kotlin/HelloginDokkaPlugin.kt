import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.jetbrains.dokka.gradle.DokkaExtension
import org.jetbrains.dokka.gradle.engine.parameters.DokkaSourceSetSpec
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class HelloginDokkaPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.dokka")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {

                }
                sourceSets.iosMain.dependencies {

                }
            }

            extensions.configure<DokkaExtension> {
                moduleName.set(name)

                dokkaPublications.getByName("html") {
                    outputDirectory.set(target.mkdir("build/dokka"))
                }
                extensions.configure<NamedDomainObjectContainer<DokkaSourceSetSpec>> {
                    named("commonMain") {
                        displayName.set("Common")
                    }
                    named("androidMain") {
                        displayName.set("Android")
                    }
                    named("iosMain") {
                        displayName.set("iOS")
                    }
                }
            }
        }
    }
}