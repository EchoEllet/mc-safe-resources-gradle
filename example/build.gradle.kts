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

java.sourceSets.main {
    java.srcDir(tasks.generateLangKeys.map { it.outputs.files.singleFile })
    java.srcDir(tasks.generateSoundKeys.map { it.outputs.files.singleFile })
}

tasks.compileJava {
    dependsOn(tasks.generateLangKeys)
    dependsOn(tasks.generateSoundKeys)
}

mcSafeResources {
    namespace.set(exampleModId)
    outputLanguage.set(JAVA)
}
