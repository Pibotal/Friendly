import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.3.61")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.3.61")
    }
    repositories {
        maven {
            url = uri("https://plugins.gradle.org/m2/")
        }
    }
}

plugins {
    kotlin("jvm") version "1.3.72"
    kotlin("plugin.spring") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    id("org.springframework.boot") version "2.3.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("com.github.node-gradle.node") version "2.2.4"
}

allprojects {
    group = "io.pibotal"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-allopen")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "com.github.node-gradle.node")

    group = "io.pibotal"
    version = "0.0.1-SNAPSHOT"

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
        testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")

    }

    tasks {
        compileKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
            dependsOn(processResources)
        }

        compileTestKotlin {
            kotlinOptions {
                freeCompilerArgs = listOf("-Xjsr305=strict")
                jvmTarget = "11"
            }
        }
    }
}

project(":common"){
    val jar: Jar by tasks
    val bootJar: BootJar by tasks

    bootJar.enabled = false
    jar.enabled = true
}

project(":api") {

    dependencies {

        implementation(project(":common"))
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}