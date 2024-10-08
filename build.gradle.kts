import java.util.Properties

buildscript {
    dependencies {
        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.15.2")
    }
}

plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.compose).apply(false)
    alias(libs.plugins.cocoapods).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.serialization).apply(false)
    id("org.jetbrains.dokka") version "1.9.20"
    `maven-publish`
    signing
}


subprojects {
    if (name.startsWith("hellogin") && name != "hellogin-bom") {
        apply(plugin = "org.jetbrains.dokka")
        apply(plugin = "maven-publish")
        apply(plugin = "signing")

        // Stub secrets to let the project sync and build without the publication values set up
        ext["signing.keyId"] = null
        ext["signing.password"] = null
        ext["signing.secretKeyRingFile"] = null
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
            ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
            ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
            ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
            ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
            ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
        }

        fun getExtraString(name: String) = ext[name]?.toString()

        val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

        val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
            dependsOn(dokkaHtml)
            archiveClassifier.set("javadoc")
            from(dokkaHtml.outputDirectory)
        }

        publishing {
            // Configure maven central repository
            repositories {
                maven {
                    name = "sonatype"
                    url = if (project.version.toString().endsWith("SNAPSHOT")) {
                        uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                    } else {
                        uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                    }
                    credentials {
                        username = getExtraString("ossrhUsername")
                        password = getExtraString("ossrhPassword")
                    }
                }
            }

            // Configure all publications
            publications.withType<MavenPublication> {
                artifact(javadocJar.get())

                // Provide artifacts information requited by Maven Central
                pom {
                    name.set(this@subprojects.name)
                    description.set("HelLogin Library")
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
            }
        }
        signing {
            sign(publishing.publications)
        }
        // TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
        project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
            dependsOn(project.tasks.withType(Sign::class.java))
        }
    }
}