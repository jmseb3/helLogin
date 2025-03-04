
plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
}

kotlin {
    sourceSets {
        iosMain.dependencies {

        }
    }
}

android {
    namespace = "com.wonddak.hellogin.core"
}


dokka {
    moduleName.set("Hellogin")

    dokkaPublications.html {
        outputDirectory.set(project.mkdir("build/dokka"))
    }

    dokkaSourceSets {
        this.commonMain {
            displayName.set("Common")
        }
        named("androidMain") {
            displayName.set("Android")
        }
        named("iosMain") {
            displayName.set("iOS")
        }
    }
}