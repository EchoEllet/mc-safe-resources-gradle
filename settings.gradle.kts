rootProject.name = "mc-safe-resources"
include(
    "mc-safe-resources",
    "example",
)

pluginManagement {
    repositories {
        // TODO: Temporary, replace with Gradle Plugin Portal.
        //  https://github.com/EchoEllet/mc-safe-resources-gradle/issues/1
//        maven { url = uri("https://echoellet.github.io/maven-repo/") }
        maven { url = uri("./mc-safe-resources/build/local-repo") }
        gradlePluginPortal()
    }
}
