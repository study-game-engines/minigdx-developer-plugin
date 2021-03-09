/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Gradle plugin project to get you started.
 * For more details take a look at the Writing Custom Plugins chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.8.2/userguide/custom_plugins.html
 */

plugins {
    // Plugin publication plugin.
    id("com.gradle.plugin-publish") version "0.13.0"

    // Apply the Java Gradle plugin development plugin to add support for developing Gradle plugins
    `java-gradle-plugin`

    `maven-publish`

    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.70"
}

group = "com.github.minigdx"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.jetbrains.kotlin.multiplatform:org.jetbrains.kotlin.multiplatform.gradle.plugin:1.3.70")
    implementation("org.jlleitschuh.gradle.ktlint:org.jlleitschuh.gradle.ktlint.gradle.plugin:9.2.1")

    implementation(platform("me.champeau.jdoctor:jdoctor-bom:0.1"))
    implementation("me.champeau.jdoctor:jdoctor-core")
    implementation("me.champeau.jdoctor:jdoctor-utils:0.1")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

gradlePlugin {
    // Define the plugin
    val developer by plugins.creating {
        id = "com.github.minigdx.gradle.plugin.developer"
        implementationClass = "com.github.minigdx.gradle.plugin.MiniGdxDeveloperGradlePlugin"
    }
}

pluginBundle {
    website = "https://github.com/minigdx/minigdx-gradle-plugin"
    vcsUrl = "https://github.com/minigdx/minigdx-gradle-plugin"

    (plugins) {
        // first plugin
        "developer" {
            // id is captured from java-gradle-plugin configuration
            displayName = "MiniGDX Developer plugin"
            description = """Configure MiniGDX libs with a common set of configuration and tasks.
                | The usage is mainly for MiniGDX contributors.
            """.trimMargin()
            tags = listOf("minigdx", "developer")
        }
    }
}

val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

// Add a source set for the functional test suite
val functionalTestSourceSet = sourceSets.create("functionalTest") {
}

gradlePlugin.testSourceSets(functionalTestSourceSet)
configurations["functionalTestImplementation"].extendsFrom(configurations["testImplementation"])

// Add a task to run the functional tests
val functionalTest by tasks.registering(Test::class) {
    testClassesDirs = functionalTestSourceSet.output.classesDirs
    classpath = functionalTestSourceSet.runtimeClasspath
}

tasks.check {
    // Run the functional tests as part of `check`
    dependsOn(functionalTest)
}
