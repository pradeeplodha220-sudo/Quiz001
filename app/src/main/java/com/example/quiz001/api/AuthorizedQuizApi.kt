package com.example.quiz001.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

data class ApiCheckResult(val code: Int, val body: String, val elapsedMs: Long)

object AuthorizedQuizApi {
    /**
     * Connectivity check for an API you own or are explicitly authorized to test.
     * This performs only GET; it does not submit answers or probe correctness.
     */
    suspend fun checkGet(endpoint: String, bearerToken: String = ""): ApiCheckResult =
        withContext(Dispatchers.IO) {
            val clean = endpoint.trim()
            require(clean.startsWith("https://", ignoreCase = true)) {
                "Use an HTTPS endpoint."
            }
            val url = URL(clean)
            val started = System.nanoTime()
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 10_000
                readTimeout = 10_000
                setRequestProperty("Accept", "application/json")
                if (bearerToken.isNotBlank()) {
                    setRequestProperty("Authorization", "Bearer $bearerToken")
                }
            }
            try {
                val code = connection.responseCode
                val stream = if (code in 200..399) connection.inputStream else connection.errorStream
                val body = stream?.bufferedReader()?.use { it.readText().take(4000) }
                    ?: "(empty response)"
                ApiCheckResult(code, body, (System.nanoTime() - started) / 1_000_000)
            } finally {
                connection.disconnect()
            }
        }
}
