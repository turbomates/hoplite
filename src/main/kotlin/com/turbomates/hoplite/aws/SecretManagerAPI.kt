package com.turbomates.hoplite.aws

import aws.sdk.kotlin.runtime.auth.credentials.StaticCredentialsProvider
import aws.sdk.kotlin.services.secretsmanager.SecretsManagerClient
import aws.sdk.kotlin.services.secretsmanager.model.GetSecretValueRequest
import aws.smithy.kotlin.runtime.auth.awscredentials.Credentials
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class SecretManagerAPI(
    private val private: String,
    private val secret: String,
    private val region: String
) {
    suspend fun secret(name: String): Map<String, String> {
        return SecretsManagerClient {
            credentialsProvider = StaticCredentialsProvider(Credentials(private, secret))
            region = this@SecretManagerAPI.region
        }.use { secretsClient ->
            val secretValue = secretsClient.getSecretValue(GetSecretValueRequest { secretId = name })
            val jsonElement =
                secretValue.secretString?.let { json.decodeFromString<JsonObject>(it) } ?: JsonObject(emptyMap())
            jsonElement.mapValues { element ->
                element.value.jsonPrimitive.content
            }
        }
    }

    companion object {
        private val json = Json
    }
}
