import com.wonddak.hellogin.HelloginVersionPlugin
import com.wonddak.hellogin.getAllLibVersionList
import java.util.Properties

plugins {
    `maven-publish`
    signing
}


afterEvaluate {
    // Stub secrets to let the project sync and build without the publication values set up
    ext["ossrhUsername"] = null
    ext["ossrhPassword"] = null

    // Grabbing secrets from local.properties file or from environment variables, which could be used on CI
    val secretPropsFile = project.rootProject.file("local.properties")
    if (secretPropsFile.exists()) {
        secretPropsFile.reader().use {
            Properties().apply {
                load(it)
            }
        }.onEach { (name, value) ->
            ext[name.toString()] = value
        }
    } else {
        ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
        ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    }

    fun getExtraString(name: String) = ext[name]?.toString()

    publishing {
        repositories {
            maven {
                name = "sonatype"
                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = getExtraString("ossrhUsername")
                    password = getExtraString("ossrhPassword")
                }
            }
        }

        publications {
            create<MavenPublication>("bom") {
                groupId = "io.github.jmseb3"
                artifactId = "hellogin-bom"
                version = "1.1.0"

                pom {
                    name.set("hellogin-bom")
                    description.set("HelLogin Library Bom")
                    url.set("https://github.com/jmseb3/helLogin")

                    licenses {
                        license {
                            name.set("Apache License 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0")
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
                // Provide artifacts information requited by Maven Central
                pom.withXml {
                    val dependenciesNode = asNode().appendNode("dependencyManagement")
                    val dependenciesElement = dependenciesNode.appendNode("dependencies")

                    getAllLibVersionList().forEach { (name, version) ->
                        dependenciesElement.appendNode("dependency").apply {
                            appendNode("groupId", "io.github.jmseb3")
                            appendNode("artifactId", name)
                            appendNode("version", version)
                        }
                    }
                }
            }
        }
    }
    signing {
        sign(publishing.publications)
    }
}