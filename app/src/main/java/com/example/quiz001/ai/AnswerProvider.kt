package com.example.quiz001.ai
interface AnswerProvider { suspend fun answer(questionAndOptions:String):AnswerResult }
