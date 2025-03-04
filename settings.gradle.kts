pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "HeLLogin"
//include(":sample:composeApp")

//include(":hellogin-bom")
include(":hellogin-core")
include(":hellogin-core-network")
include(":hellogin-core-ui")

include(":hellogin-apple")
include(":hellogin-google")
include(":hellogin-github")

include(":hellogin-apple-ui")
include(":hellogin-google-ui")
include(":hellogin-github-ui")
