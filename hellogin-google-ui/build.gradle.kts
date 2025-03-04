plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.compose)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":hellogin-google"))
            implementation(project(":hellogin-core-ui"))
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.google"
}
