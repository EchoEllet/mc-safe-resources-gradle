rootProject.name = "minecraft-safe-resources"
include(
    "minecraft-safe-resources",
    "example",
)

pluginManagement {
    repositories {
        // TODO: Temporary, replace with Gradle Plugin Portal.
        //  https://github.com/EchoEllet/minecraft-safe-resources-gradle/issues/1
//        maven { url = uri("https://echoellet.github.io/maven-repo/") }
        maven { url = uri("./minecraft-safe-resources/build/local-repo") }
        gradlePluginPortal()
    }
}
