package com.riteshmaagadh.lla_base.ui.quiz

interface QuizCallbacks {
    fun shouldButtonEnabled()
    fun onLanguageSelected(langCode: String)
    fun onReasonSelected(reason: String)
    fun onDailyGoalSelected(dailyGoalInMin: Int)
}