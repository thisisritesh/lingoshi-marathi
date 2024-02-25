package com.riteshmaagadh.lla_base.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UserData(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var inProgressLessonId: String,
    var inProgressCompletedCount: Int,
    var streakCount: Int,
    var availableLivesCount: Int,
    var dailyGoal: Int,
    var completedLessons: MutableList<CompletedLesson>
) {

    data class CompletedLesson(
        var lessonId: String,
        var lessonName: LessonName
    ) {
        data class LessonName(
            var hi: String,
            var en: String,
            var bn: String,
            var te: String
        )

        fun getLessonName(lang: String): String {
            return when (lang) {
                "hi" -> lessonName.hi
                "en" -> lessonName.en
                "bn" -> lessonName.bn
                "te" -> lessonName.te
                else -> lessonName.en
            }
        }

    }


}
