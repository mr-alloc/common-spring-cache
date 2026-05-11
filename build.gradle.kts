plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "io.alloc"
version = "0.0.1-SNAPSHOT"
description = "common-spring-cache"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    //AspectJ
    compileOnly("org.aspectj:aspectjweaver")
    //Spring Cache Abstraction
    compileOnly("org.springframework.boot:spring-boot-starter-cache")
    //Spring Data Redis
    compileOnly("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("tools.jackson.module:jackson-module-kotlin")

    testImplementation("org.springframework.boot:spring-boot-starter-cache-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.6")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = true
}