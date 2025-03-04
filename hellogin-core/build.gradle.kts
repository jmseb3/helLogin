
plugins {
    alias(libs.plugins.hellogin.version)
    alias(libs.plugins.hellogin.kmp.library)
    alias(libs.plugins.hellogin.maven)
    alias(libs.plugins.hellogin.dokka)
}

android {
    namespace = "com.wonddak.hellogin.core"
}