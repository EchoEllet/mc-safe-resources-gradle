plugins {
    `kotlin-dsl`
    embeddedKotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
    alias(libs.plugins.gradlePluginPublish)
}

group = "dev.echoellet"
version = libs.versions.mcSafeResources.get()
description =
    "A Gradle plugin to generate Java/Kotlin object constants to reference Minecraft resources or assets, such as en_us.json in code in a type-safe way without any hardcoding"

repositories {
    mavenCentral()
}

dependencies {
    implementation(gradleApi())
    implementation(libs.gson)
}

val pluginId = "dev.echoellet.mc-safe-resources"

gradlePlugin {
    val gitHubRepo = "https://github.com/EchoEllet/mc-safe-resources-gradle"
    website = gitHubRepo
    vcsUrl = gitHubRepo

    plugins.create("mcSafeResourcesPlugin") {
        id = pluginId
        implementationClass = "dev.echoellet.mc_safe_resources.McSafeResourcesPlugin"
        displayName = "MC Safe Resources"
        description = project.description
        version = project.version
        tags = listOf("minecraft", "mod", "resources", "generator", "json", "type-safe")
    }
}

java {
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Version" to version))
    }
}

// For testing purposes https://docs.gradle.org/current/userguide/preparing_to_publish.html#local_publishing_and_testing
publishing {
    repositories {
        maven { url = uri(layout.buildDirectory.dir("local-repo")) }
    }
}

fun replaceVersionInFile(path: String) {
    val file = rootProject.file(path)
    val content = file.readText()
    val versionPattern = Regex("""id\("$pluginId"\)\s+version\s*\(\s*".*"\s*\)""")

    val newContent = versionPattern.replace(content) {
        """id("$pluginId") version("${project.version}")"""
    }

    if (content != newContent) {
        file.writeText(newContent)
        println("Updated version of $pluginId in $path to ${project.version}")
    } else {
        println("No version string found to replace for $pluginId in $path")
    }
}

replaceVersionInFile("README.md")
