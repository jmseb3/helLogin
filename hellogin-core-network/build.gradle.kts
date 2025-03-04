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
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.ktor.serialization.json)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.wonddak.hellogin.core"
}
