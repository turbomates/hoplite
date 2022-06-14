rootProject.name = "hoplite"


enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    versionCatalogs {
        create("deps") {
            version("ktor", "2.0.2")
            version("detekt", "1.21.0-RC1")
            version("hoplite", "2.1.5")
            version("kotlin", "1.6.20")
            version("secretsmanager", "0.16.1-beta")
            version("test_logger", "3.0.0")
            version("kotlin_serialization_json", "1.3.3")
            version("nexus_staging", "0.30.0")
            version("mockito-kotlin", "4.0.0")

            library("ktor_client_content_negotiation", "io.ktor", "ktor-client-content-negotiation").versionRef("ktor")
            library("ktor_client_cio", "io.ktor", "ktor-client-cio").versionRef("ktor")
            library("ktor_client_serialization", "io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            library("kotlin_test", "org.jetbrains.kotlin", "kotlin-test-junit5").versionRef("kotlin")
            library("kotlin_serialization_json", "org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlin_serialization_json")
            library("hoplite", "com.sksamuel.hoplite", "hoplite-core").versionRef("hoplite")
            library("secretsmanager", "aws.sdk.kotlin", "secretsmanager").versionRef("secretsmanager")
            library("mockito_kotlin", "org.mockito.kotlin", "mockito-kotlin").versionRef("mockito-kotlin")
            library("detekt_formatting", "io.gitlab.arturbosch.detekt", "detekt-formatting").versionRef("detekt")
            bundle(
                "ktor_client", listOf(
                    "ktor_client_cio",
                    "ktor_client_serialization",
                    "ktor_client_content_negotiation"
                )
            )

            plugin("kotlin_serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("test_logger", "com.adarshr.test-logger").versionRef("test_logger")
            plugin("detekt", "io.gitlab.arturbosch.detekt").versionRef("detekt")
            plugin("nexus_release", "io.codearte.nexus-staging").versionRef("nexus_staging")
        }
    }
}
