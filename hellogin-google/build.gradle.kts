
plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
}

kotlin {
    cocoapods {
        pod("GoogleSignIn") {
            version = "8.0"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project(":hellogin-core"))
        }

        androidMain.dependencies {
            api(libs.bundles.android.google)
        }

    }
}

android {
    namespace = "com.wonddak.hellogin.google"
}
