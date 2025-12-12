rootProject.name = "mc-safe-resources"

pluginManagement {
    repositories {
        maven { url = uri("./mc-safe-resources/build/local-repo") }
        gradlePluginPortal()
    }
}

include(
    "mc-safe-resources",
    "example",
)
