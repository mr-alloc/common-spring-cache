import org.jreleaser.model.Active
import java.util.Properties

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.spring") version "2.2.21"
    id("io.spring.dependency-management") version "1.1.7"
    id ("org.jreleaser") version "1.20.0"
    id("maven-publish")
}


val projectEncoding = "UTF-8"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    withSourcesJar()
    withJavadocJar()
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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

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

val publishProps = Properties().apply {
    file("publish-info.properties").inputStream().use { load(it) }
}

group = publishProps.getProperty("GROUP_ID")
version = findProperty("VERSION") as? String ?: ""

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = publishProps["GROUP_ID"] as String
            artifactId = publishProps["ARTIFACT_ID"] as String

            from(components["java"])

            pom {
                name        = publishProps["ARTIFACT_ID"] as String
                description = publishProps["DESCRIPTION"] as String
                url         = publishProps["URL"] as String

                licenses {
                    license {
                        name   = publishProps["LICENSE_NAME"] as String
                        url    = publishProps["LICENSE_URL"] as String
                    }
                }

                developers {
                    developer {
                        id      = publishProps["DEVELOPER_ID"] as String
                        name    = publishProps["DEVELOPER_NAME"] as String
                        email  = publishProps["DEVELOPER_EMAIL"] as String
                    }
                }

                scm {
                    connection            = publishProps["SCM_CONNECTION"] as String
                    developerConnection   = publishProps["SCM_DEVELOPER_CONNECTION"] as String
                    url                   = publishProps["SCM_URL"] as String
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("staging-deploy"))
        }
    }
}


jreleaser {
    //Version must be injected for based on tag versioning
    version = System.getenv("VERSION")
    signing {
        active = Active.ALWAYS
        armored = true
    }
    deploy {
        maven {
            mavenCentral {
                create("sonatype") {
                    active = Active.ALWAYS
                    url = "https://central.sonatype.com/api/v1/publisher"
                    stagingRepository("build/staging-deploy")
                }
            }
        }
    }
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}
// 컴파일 인코딩 설정
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

// https://docs.gradle.org/current/userguide/publishing_setup.html#publishing_overview 참고
// ':signMavenJavaPublication' because it has no configured signatory: 키서버에 키가 없음