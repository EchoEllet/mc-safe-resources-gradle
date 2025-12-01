rootProject.name = "minecraft-safe-resources"
include(
    "minecraft-safe-resources",
    "example",
)

pluginManagement {
    repositories {
        maven { url = uri("https://echoellet.github.io/maven-repo/") }
        gradlePluginPortal()
    }
}
