import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm").version(deps.versions.kotlin.asProvider().get())
    alias(deps.plugins.kotlin.serialization).version(deps.versions.kotlin.asProvider().get())
    alias(deps.plugins.detekt)
    alias(deps.plugins.nexus.release)
    alias(deps.plugins.test.logger)
    `maven-publish`
    signing
}

group = "com.turbomates.hoplite"
version = System.getenv("RELEASE_VERSION") ?: "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(deps.bundles.ktor.client)
    implementation(deps.kotlin.serialization.json)
    implementation(deps.secretsmanager)
    implementation(deps.mockito.kotlin)
    api(deps.hoplite)
    detektPlugins(deps.detekt.formatting)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

detekt {
    toolVersion = deps.versions.detekt.get()
    autoCorrect = false
    parallel = true
    config = files("detekt.yml")
}
tasks.named("check").configure {
    this.setDependsOn(this.dependsOn.filterNot {
        it is TaskProvider<*> && it.name == "detekt"
    })
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "hoplite"
            groupId = "com.turbomates.kotlin"
            from(components["java"])
            pom {
                packaging = "jar"
                name.set("Hoplite extensions")
                url.set("https://github.com/turbomates/hoplite")
                description.set("Extensions for Hoplite config library")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/turbomates/hoplite/blob/main/LICENSE")
                    }
                }

                scm {
                    connection.set("scm:https://github.com/turbomates/hoplite.git")
                    developerConnection.set("scm:git@github.com:turbomates/hoplite.git")
                    url.set("https://github.com/turbomates/hoplite")
                }

                developers {
                    developer {
                        id.set("shustrik")
                        name.set("Vadim Golodko")
                        email.set("vadim.golodko@gmail.com")
                    }
                }
            }
        }
    }
    repositories {
        maven {
            val releasesUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl
            credentials {
                username = System.getenv("ORG_GRADLE_PROJECT_SONATYPE_USERNAME") ?: project.properties["ossrhUsername"].toString()
                password = System.getenv("ORG_GRADLE_PROJECT_SONATYPE_PASSWORD") ?: project.properties["ossrhPassword"].toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

nexusStaging {
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
    username = System.getenv("ORG_GRADLE_PROJECT_SONATYPE_USERNAME") ?: project.properties["ossrhUsername"].toString()
    password = System.getenv("ORG_GRADLE_PROJECT_SONATYPE_PASSWORD") ?: project.properties["ossrhPassword"].toString()
}
