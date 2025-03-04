plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
    alias(libs.plugins.serialization)
}

kotlin {

    sourceSets {
        commonMain.dependencies {
            implementation(project(":hellogin-core"))
            implementation(project(":hellogin-core-network"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.browser)
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.github"
}
