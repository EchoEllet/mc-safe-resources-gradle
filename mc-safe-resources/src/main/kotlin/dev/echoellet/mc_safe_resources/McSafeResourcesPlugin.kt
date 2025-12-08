package dev.echoellet.mc_safe_resources

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

@Suppress("unused")
abstract class McSafeResourcesPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "mcSafeResources",
            McSafeResourcesExtension::class.java,
        )

        val modAssetsDirPath = extension.namespace.map { modId ->
            "src/main/resources/assets/$modId"
        }

        // TODO: Provide generateModAssetPaths https://github.com/EchoEllet/dragonfist-legacy/blob/e335f3714da61c435d57e7ddd1d80c5c45836d04/build.gradle.kts#L207-L264

        for (task in McSafeResourcesGenerateTask.entries) {
            project.tasks.register<GenerateJsonKeysTask>("generate${task.outputClassName}") {

                val resourcePathProvider = modAssetsDirPath.map { dir ->
                    "$dir/${task.relativeResourcePath}"
                }

                inputResourceFile.set(project.file(resourcePathProvider))
                outputClassName.set(task.outputClassName)
                outputLanguage.set(extension.outputLanguage)
                outputClassDescription.set(resourcePathProvider.map { buildGeneratedObjectDescription(it) })
                outputPackage.set(extension.outputPackage.orElse(getDefaultOutputPackage(project, extension.namespace.get())))
                keyNamespaceToStrip.set(extension.namespace)
                useJetBrainsAnnotations.set(hasJetBrainsAnnotationsDependency(project))
            }
        }
    }

    private fun hasJetBrainsAnnotationsDependency(project: Project): Boolean {
        val userConfigs = listOf("implementation", "api", "compileOnly", "testImplementation", "testCompileOnly")

        return userConfigs.any { configName ->
            project.configurations.findByName(configName)
                ?.allDependencies
                ?.any { dep ->
                    dep.group == "org.jetbrains" && dep.name == "annotations"
                } == true
        }
    }

    private fun getDefaultOutputPackage(project: Project, modId: String): String {
        return "${project.group}.${modId}.generated"
    }

    private fun buildGeneratedObjectDescription(resourcePath: String): String {
        return buildString {
            append("A generated object that represents the keys in `$resourcePath` resource file,"); appendLine()
            append("to avoid hardcoding the keys across the codebase, which is error-prone, inefficient, and less type-safe.")
            appendLine(); appendLine()
            append("**Note:** Breaking changes may occur. This approach is preferred over hardcoding keys, which can lead to runtime errors or crashes.")
        }
    }
}
