rootProject.name = "HeLLogin"
include(":sample:composeApp")

include(":hellogin-bom")

include(":hellogin-core")
include(":hellogin-core-network")
include(":hellogin-google")
include(":hellogin-github")

include(":hellogin-core-ui")
include(":hellogin-google-ui")


pluginManagement {
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
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
        mavenLocal()
    }
}
