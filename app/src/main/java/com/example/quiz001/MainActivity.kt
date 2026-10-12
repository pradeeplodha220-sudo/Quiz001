package com.example.quiz001

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.quiz001.api.AuthorizedQuizApi
import kotlinx.coroutines.launch

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (16 * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad, pad, pad)
        }
        root.addView(TextView(this).apply {
            text = "Quiz001 — API Test Assistant"
            textSize = 22f
        })
        root.addView(TextView(this).apply {
            text = "For your own quiz API or an endpoint you are authorized to test."
            textSize = 14f
        })

        val endpoint = EditText(this).apply {
            hint = "HTTPS health/status endpoint (e.g. https://your-server.example/health)"
            setSingleLine(true)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_URI
        }
        root.addView(endpoint, matchWrap())

        val token = EditText(this).apply {
            hint = "Optional Bearer token (kept in memory only)"
            setSingleLine(true)
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        root.addView(token, matchWrap())

        val check = Button(this).apply { text = "Test authorized API (GET)" }
        root.addView(check, matchWrap())

        val question = EditText(this).apply {
            hint = "Paste a practice question and its options here"
            minLines = 4
            gravity = Gravity.TOP
        }
        root.addView(question, LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f
        ))

        val info = TextView(this).apply {
            text = "API log will appear here. AI answer providers are not connected in this build."
            textSize = 14f
        }
        root.addView(info)
        check.setOnClickListener {
            val url = endpoint.text.toString().trim()
            if (url.isBlank()) {
                info.text = "Enter your own authorized HTTPS status endpoint first."
                return@setOnClickListener
            }
            check.isEnabled = false
            info.text = "Checking endpoint…"
            lifecycleScope.launch {
                try {
                    val result = AuthorizedQuizApi.checkGet(url, token.text.toString())
                    info.text = "HTTP ${result.code} • ${result.elapsedMs} ms\n\n${result.body}"
                } catch (e: Exception) {
                    info.text = "API check failed: ${e.message ?: "Unknown error"}"
                } finally {
                    check.isEnabled = true
                }
            }
        }
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun matchWrap() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ).apply { bottomMargin = (8 * resources.displayMetrics.density).toInt() }
}
