plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.mcSafeResources)
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.jetbrainsAnnotations)
}

group = "org.example"
val modId = "my_mod_id"

kotlin.sourceSets.main.get().kotlin.srcDirs(
    tasks.generateLangKeys.map { it.outputs.files.singleFile },
    tasks.generateSoundKeys.map { it.outputs.files.singleFile }
)

tasks.compileKotlin { dependsOn(tasks.generateLangKeys, tasks.generateSoundKeys) }

mcSafeResources {
    namespace.set(modId)
    outputLanguage.set(KOTLIN)
    keyStringReplacements.set(mapOf(
        $$"${modId}" to modId,
    ))
}

tasks.withType<ProcessResources> {
    val replaceProperties = mapOf(
        "modId" to modId,
        "specialKey" to $$"${specialKey}",
    )
    inputs.properties(replaceProperties)
    filesMatching(listOf("**/*.json")) { expand(replaceProperties) }
}
