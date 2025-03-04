
plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
    alias(libs.plugins.hellogin.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(project(":hellogin-core"))
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.core"
}
