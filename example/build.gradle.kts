import dev.echoellet.minecraft_safe_resources.GenerateJsonKeysTask
import dev.echoellet.minecraft_safe_resources.OutputLanguage

plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.minecraftSafeResources)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly(libs.jetbrainsAnnotations)
}

group = "org.example"
val modId = "my_mod_id"

val modAssetsDirPath = "src/main/resources/assets/$modId"
val enUsResourcePath = "$modAssetsDirPath/lang/en_us.json"

val generateLangKeys = tasks.register<GenerateJsonKeysTask>("generateLangKeys") {
    outputLanguage.set(OutputLanguage.JAVA)
    outputPackage.set("${project.group}.generated")
    inputResourceFile.set(project.file(enUsResourcePath))
    keyNamespaceToStrip.set(modId)
    outputClassDescription.set(buildString {
        append("A generated object that represents the keys in `$enUsResourcePath` resource file,")
        appendLine()
        append("to avoid hardcoding the keys across the codebase, which is error-prone, inefficient, and less type-safe.")
        appendLine(); appendLine();
        append("**Note:** Breaking changes may occur. This approach is preferred over hardcoding keys, which can lead to runtime errors or crashes.")
    })
    outputClassName.set("LangKeys")
}

java.sourceSets.main.get().java.srcDir(generateLangKeys.map { it.outputs.files.singleFile })
tasks.compileJava.get().dependsOn(generateLangKeys)

