import org.jetbrains.compose.ExperimentalComposeLibrary
import com.wonddak.hellogin.AppConfig
import com.wonddak.hellogin.moduleVersions
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.cocoapods)
    alias(libs.plugins.android.library)
    alias(libs.plugins.serialization)
    HelloginVersionPlugin
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                    freeCompilerArgs.add("-Xjdk-release=${JavaVersion.VERSION_1_8}")
                }
            }
        }
        //https://www.jetbrains.com/help/kotlin-multiplatform-dev/compose-test.html
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant {
            sourceSetTree.set(KotlinSourceSetTree.test)
            dependencies {
                debugImplementation(libs.androidx.testManifest)
                implementation(libs.androidx.junit4)
            }
        }
        publishLibraryVariants("release")
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()


    cocoapods {
        version = moduleVersions[project.name]
        summary = "Hellogin Core Network Pods"
        homepage = AppConfig.HOME_PAGE
        ios.deploymentTarget = AppConfig.deploymentTarget
        framework {
            baseName = "helloginCoreNetwork"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(libs.ktor.client.core)
            api(libs.ktor.client.auth)
            api(libs.ktor.client.negotiation)
            api(libs.ktor.serialization.json)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
        }
        androidMain.dependencies {
            api(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            api(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.core"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/res")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        //enables a Compose tooling support in the AndroidStudio
        compose = true
    }
}
