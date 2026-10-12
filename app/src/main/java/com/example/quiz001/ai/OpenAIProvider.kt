package com.example.quiz001.ai
class OpenAIProvider:AnswerProvider { override suspend fun answer(questionAndOptions:String):AnswerResult = error("Configure official OpenAI client and secure key handling first.") }
