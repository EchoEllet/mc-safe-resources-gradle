package dev.echoellet.mc_safe_resources

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class McSafeResourcesExtension @Inject constructor(objects: ObjectFactory) {
    // Removes the need for "OutputLanguage" import

    @Suppress("PropertyName")
    val KOTLIN = OutputLanguage.KOTLIN

    @Suppress("PropertyName")
    val JAVA = OutputLanguage.JAVA

    val modId: Property<String> = objects
        .property(String::class.java)

    val outputLanguage: Property<OutputLanguage> = objects
        .property(OutputLanguage::class.java)
        .convention(OutputLanguage.JAVA)

    val outputPackage: Property<String> = objects
        .property(String::class.java)
}
