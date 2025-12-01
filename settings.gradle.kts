rootProject.name = "minecraft-safe-resources"
include(
    "plugin",
    "example",
)

pluginManagement {
    repositories {
        maven { url = uri("https://echoellet.github.io/maven-repo/") }
        gradlePluginPortal()
    }
}
