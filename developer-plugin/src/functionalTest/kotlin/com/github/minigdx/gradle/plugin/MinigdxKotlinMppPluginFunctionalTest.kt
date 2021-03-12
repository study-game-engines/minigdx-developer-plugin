/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package com.github.minigdx.gradle.plugin

import java.io.File
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * A simple functional test for the 'com.github.minigdx.gradle.plugin.greeting' plugin.
 */
class MinigdxKotlinMppPluginFunctionalTest {

    @get:Rule
    val temporaryFolder: TemporaryFolder = TemporaryFolder()

    @Test fun `can build`() {
        // Setup the test build
        val projectDir = temporaryFolder.newFolder("build", "functionalTest")
        projectDir.mkdirs()
        projectDir.resolve("settings.gradle").writeText("")
        projectDir.resolve("build.gradle").writeText("""
            plugins {
                id('com.github.minigdx.gradle.plugin.developer.mpp')
            }
        """)

        // Run the build
        val runner = GradleRunner.create()
        runner.forwardOutput()
        runner.withPluginClasspath()
        runner.withArguments("build")
        runner.withProjectDir(projectDir)
        val result = runner.build();

        // Verify the result
        assertEquals(TaskOutcome.SUCCESS, result.task(":build")?.outcome)
    }
}
