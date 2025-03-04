plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project(":hellogin-core"))
        }
        androidMain.dependencies {
            implementation(libs.androidx.browser)
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.apple"
}
