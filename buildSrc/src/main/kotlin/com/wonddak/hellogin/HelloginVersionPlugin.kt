package com.wonddak.hellogin

import org.gradle.api.Plugin
import org.gradle.api.Project

private val moduleVersions: MutableMap<String, String> = mutableMapOf(
    "hellogin-core" to "1.0.1",
    "hellogin-core-network" to "1.0.0",
    "hellogin-core-ui" to "1.0.1",
    "hellogin-google" to "1.0.1",
    "hellogin-google-ui" to "1.0.1",
    "hellogin-github" to "1.0.0",
    "hellogin-github-ui" to "1.0.0",
    "hellogin-apple" to "1.0.0",
)

fun getAllLibVersionList() : List<Pair<String,String>> {
    return moduleVersions.map { it.toPair() }
}

class HelloginVersionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val isSnapshot = project.findProperty("isSnapshot")?.toString()?.toBoolean() ?: false

        project.group = "io.github.jmseb3"

        val defaultVersion = moduleVersions[project.name]
            ?: throw IllegalArgumentException("Module ${project.name} is not defined in moduleVersions.")

        project.version = if (isSnapshot) {
            "$defaultVersion-SNAPSHOT"
        } else {
            defaultVersion
        }
    }
}
