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

kotlin.sourceSets.main.get().kotlin.srcDir(tasks.generateLangKeys.map { it.outputs.files.singleFile })
tasks.compileKotlin.get().dependsOn(tasks.generateLangKeys)

mcSafeResources {
    modId.set(exampleModId)
    outputLanguage.set(KOTLIN)
}
