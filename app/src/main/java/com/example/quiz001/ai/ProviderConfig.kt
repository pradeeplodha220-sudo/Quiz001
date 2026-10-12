package com.example.quiz001.ai
enum class Provider { GROQ, GEMINI, OPENAI }
data class ProviderConfig(val provider: Provider, val model: String, val endpoint: String, val apiKey: String = "")
// Never ship real API keys in the APK. Prefer a backend proxy for production.
