import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.intellij.tasks.PatchPluginXmlTask

plugins {
    id("org.jetbrains.intellij") version "0.4.10"
    kotlin("jvm") version "1.3.41"
}

group = "ua.tarch64"
version = "1.0.0"

repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile("io.socket:socket.io-client:1.0.0")
}

intellij { version = "2019.2.1" }

tasks.getByName<PatchPluginXmlTask>("patchPluginXml") {
    changeNotes("Add change notes here.<br><em>most HTML tags may be used</em>")
}

tasks.withType<KotlinCompile> {
    kotlinOptions { kotlinOptions.jvmTarget = "1.8" }
}