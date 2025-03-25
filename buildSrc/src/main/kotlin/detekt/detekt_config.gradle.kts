import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

plugins {
    id("io.gitlab.arturbosch.detekt")
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}

val projectSource = file(projectDir)
val detektConfigFile = files("$rootDir/detekt/config.yml")
val detektBaselineFile = file("$rootDir/detekt/baseline.xml")
val detektReportsDir = file("$rootDir/detekt/reports")
val kotlinFiles = "**/*.kt"
val resourceFiles = "**/res/**"
val buildFiles = "**/build/**"

tasks.register<Detekt>("detektAll") {
    description = "Custom detekt task for running in all modules in one command"
    buildUponDefaultConfig = true
    parallel = true
    ignoreFailures = false
    setSource(projectSource)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    config.setFrom(detektConfigFile)
    baseline.set(detektBaselineFile)
    reportsDir.set(detektReportsDir)

    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

tasks.register<DetektCreateBaselineTask>("detektGenerateBaseline") {
    description = "Custom detekt task to build baseline for all modules in one command"
    buildUponDefaultConfig.set(true)
    parallel.set(true)
    ignoreFailures.set(false)
    setSource(projectSource)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    config.setFrom(detektConfigFile)
    baseline.set(detektBaselineFile)
}
