plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("script-runtime"))
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.4")
}