package com.example.quiz001
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.*
class MainActivity : Activity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  val pad=(16*resources.displayMetrics.density).toInt()
  val root=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL; setPadding(pad,pad,pad,pad) }
  root.addView(TextView(this).apply { text="Quiz001 — Test Assistant"; textSize=22f })
  root.addView(TextView(this).apply { text="Authorized test mode. Paste a question to test the setup."; textSize=14f })
  val provider=Spinner(this).apply { adapter=ArrayAdapter(this@MainActivity,android.R.layout.simple_spinner_dropdown_item,listOf("Groq","Gemini","OpenAI")) }
  root.addView(provider,LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT))
  val question=EditText(this).apply { hint="Question and options"; minLines=5; gravity=android.view.Gravity.TOP }
  root.addView(question,LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1f))
  val result=TextView(this).apply { text="Suggested answer will appear here."; textSize=16f }
  root.addView(Button(this).apply { text="Validate input (demo)"; setOnClickListener { val q=question.text.toString().trim(); result.text=if(q.isBlank()) "Enter a test question first." else "Input received (${q.length} characters). Provider: ${provider.selectedItem}. API adapter not configured." } })
  root.addView(result); setContentView(root)
 }
}
