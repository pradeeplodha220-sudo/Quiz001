package com.example.quiz001.ai
data class AnswerResult(val optionLetter:String?, val optionText:String?, val explanation:String?, val confidence:Double?, val needsReview:Boolean=true)
object AnswerValidator {
 private val allowed=setOf("A","B","C","D")
 fun normalizeLetter(value:String?):String?=value?.trim()?.uppercase()?.takeIf { it in allowed }
 fun validate(result:AnswerResult, visibleOptions:Map<String,String>):AnswerResult {
  val letter=normalizeLetter(result.optionLetter)
  val matched=letter?.let { visibleOptions[it] }
  val valid=letter!=null && matched!=null && (result.optionText.isNullOrBlank() || result.optionText.equals(matched,ignoreCase=true))
  return result.copy(optionLetter=if(valid) letter else null, optionText=if(valid) matched else null, needsReview=true)
 }
}
