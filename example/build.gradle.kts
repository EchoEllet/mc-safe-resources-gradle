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
val exampleModId = "my_mod_id"

kotlin.sourceSets.main.get().kotlin.srcDirs(
    tasks.generateLangKeys.map { it.outputs.files.singleFile },
    tasks.generateSoundKeys.map { it.outputs.files.singleFile }
)

tasks.compileKotlin { dependsOn(tasks.generateLangKeys, tasks.generateSoundKeys) }

mcSafeResources {
    namespace.set(exampleModId)
    outputLanguage.set(KOTLIN)
}
