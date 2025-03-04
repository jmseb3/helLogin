import org.gradle.api.Plugin
import org.gradle.api.Project


class HelloginVersionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val isSnapshot = findProperty("isSnapshot")?.toString()?.toBoolean() ?: false

            group = "io.github.jmseb3"

            val defaultVersion = findProperty(name)
                ?: throw IllegalArgumentException("Module $name is not defined in moduleVersions.")

            version = if (isSnapshot) {
                "$defaultVersion-SNAPSHOT"
            } else {
                defaultVersion
            }
        }
    }
}
