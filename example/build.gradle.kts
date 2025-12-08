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

java.sourceSets.main.get().java.srcDir(tasks.generateLangKeys.map { it.outputs.files.singleFile })
tasks.compileJava.get().dependsOn(tasks.generateLangKeys)

mcSafeResources {
    namespace.set(exampleModId)
    outputLanguage.set(JAVA)
}
