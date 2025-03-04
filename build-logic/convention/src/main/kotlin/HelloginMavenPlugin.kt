import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class HelloginMavenPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.vanniktech.maven.publish")
            apply(plugin = "org.jetbrains.dokka")

            val projectGroup = this.group
            val projectName = this.name
            val projectVersion = this.version
            println("[배포 정보] : Group : $projectGroup | Name : $projectName | Version : $projectVersion")

            extensions.configure<MavenPublishBaseExtension> {
                coordinates(group.toString(), projectName, version.toString())
                pom {
                    name.set(projectName)
                    description.set("HelLogin Library")
                    inceptionYear.set("2025")
                    url.set("https://github.com/jmseb3/helLogin")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }

                    developers {
                        developer {
                            id.set("jmseb3")
                            name.set("WonDDak")
                            email.set("jmseb3@naver.com")
                        }
                    }
                    scm {
                        url.set("https://github.com/jmseb3/helLogin.git")
                        connection.set("git@github.com:jmseb3/helLogin.git")
                    }
                }
                configure(
                    KotlinMultiplatform(
                        javadocJar = JavadocJar.Dokka("dokkaGenerate"),
                        sourcesJar = true,
                    )
                )
            }
        }
    }
}