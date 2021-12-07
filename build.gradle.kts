import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.maslyaevalexandr"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.kotlin.link")
}

dependencies {
    implementation("space.kscience:plotlykt-server:0.5.0")
    //implementation("org.jetbrains.lets-plot:lets-plot-common:$2.2.0")
    implementation("org.jetbrains.lets-plot:lets-plot-image-export:2.2.0")
    implementation("org.jetbrains.lets-plot:lets-plot-kotlin-jvm:3.1.0")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}