import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
    application
}

group = "me.mitod"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/gluegen-rt-natives-windows-amd64.jar"))
    implementation(files("libs/gluegen-rt.jar"))
    implementation(files("libs/jogl-all-natives-windows-i586.jar"))
    implementation(files("libs/processing-core.jar"))
    implementation(files("libs/gluegen-rt-natives-windows-i586.jar"))
    implementation(files("libs/jogl-all-natives-windows-amd64.jar"))
    implementation(files("libs/jogl-all.jar"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}