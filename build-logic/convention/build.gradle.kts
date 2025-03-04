import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.wonddak.hellogin"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.tools.common)
    compileOnly(libs.maven.publish.gradle.plugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.jetbrains.compose.gradlePlugin)
    compileOnly(libs.kotlinMultiplatform.gradle.plugin)
    compileOnly(libs.cocoapod.gradle.plugin)
    compileOnly(libs.android.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
//        register("creverseMaven") {
//            id = libs.plugins.hellogin.maven.get().pluginId
//            implementationClass = "HelloginVersionPlugin"
//        }

        register("helloginKmpLibrary") {
            id = libs.plugins.hellogin.kmp.library.get().pluginId
            implementationClass = "HelloginKmpLibraryPlugin"
        }

        register("helloginVersion") {
            id = libs.plugins.hellogin.version.get().pluginId
            implementationClass = "HelloginVersionPlugin"
        }

        register("helloginCompose") {
            id = libs.plugins.hellogin.compose.get().pluginId
            implementationClass = "HelloginComposePlugin"
        }
    }
}