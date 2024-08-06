plugins {
    `kotlin-dsl` // enable the Kotlin-DSL
    `java-gradle-plugin`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("HelloginVersionPlugin") {
            id = "HelloginVersionPlugin"
            implementationClass = "com.wonddak.hellogin.HelloginVersionPlugin"
        }
    }
}