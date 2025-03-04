import com.android.build.api.dsl.LibraryExtension
import com.wonddak.hellogin.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import org.jetbrains.kotlin.gradle.plugin.cocoapods.CocoapodsExtension

class HelloginKmpLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "org.jetbrains.kotlin.multiplatform")
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.native.cocoapods")

            extensions.configure<KotlinMultiplatformExtension> {
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
                androidTarget {
                    compilations.all {
                        compileTaskProvider {
                            compilerOptions {
                                jvmTarget.set(JvmTarget.JVM_11)
                                freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_11}")
                            }
                        }
                    }

                    //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
                    @OptIn(ExperimentalKotlinGradlePluginApi::class)
                    instrumentedTestVariant {
                        sourceSetTree.set(KotlinSourceSetTree.test)
                    }

                    publishLibraryVariants("release")
                }

                iosX64()
                iosArm64()
                iosSimulatorArm64()

                extensions.configure<CocoapodsExtension> {
                    version = target.version.toString()
                    summary = "${target.name} library"
                    homepage = "https://github.com/jmseb3/helLogin"
                    ios.deploymentTarget = "13.0"
                    framework {
                        baseName = target.name
                        isStatic = true
                    }
                }

                sourceSets.commonTest.dependencies {
                    implementation(kotlin("test"))
                }
            }

            extensions.configure<LibraryExtension> {

                compileSdk = 35

                defaultConfig {
                    minSdk = 24
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                sourceSets.getByName("main").apply {
                    manifest.srcFile("src/androidMain/AndroidManifest.xml")
                    res.srcDirs("src/androidMain/res")
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_11
                    targetCompatibility = JavaVersion.VERSION_11
                }
            }

            dependencies {
                add("debugImplementation", libs.findLibrary("androidx.testManifest").get())
                add("implementation", libs.findLibrary("androidx.junit4").get())
            }
        }
    }
}