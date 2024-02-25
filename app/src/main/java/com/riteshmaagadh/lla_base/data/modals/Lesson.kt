package com.riteshmaagadh.lla_base.data.modals

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "lesson")
data class Lesson(
    @SerializedName("img")
    var img: Img,
    @SerializedName("index")
    var index: Int,
    @SerializedName("title")
    var title: Title,
    @SerializedName("title_en")
    var title_en: String,
    @SerializedName("sublessons")
    var sublessons: List<SubLesson>,
    @PrimaryKey
    @DocumentId
    @SerializedName("id")
    var lessonId: String
) {

    constructor() : this(Img("", ""), 0, Title("", "", "", ""), "", listOf(), "")

    fun putSubLessons(subLessons: List<SubLesson>) {
        this.sublessons = subLessons
    }

    data class Img(
        @SerializedName("disabled")
        var disabled: String,
        @SerializedName("enabled")
        var enabled: String
    ) {
        constructor() : this("", "")
    }

    data class Title(
        @SerializedName("bn")
        var bn: String,
        @SerializedName("en")
        var en: String,
        @SerializedName("hi")
        var hi: String,
        @SerializedName("te")
        var te: String
    ) {
        constructor() : this("", "", "", "")

        fun getTitle(language: String): String {
            return when (language) {
                "hi" -> hi
                "en" -> en
                "bn" -> bn
                "te" -> te
                else -> en
            }
        }

    }

    data class SubLesson(
        @SerializedName("index")
        var index: Long,
        @SerializedName("sublesson_id")
        var sublesson_id: String
    ) {
        constructor() : this(0, "")
    }

    fun getTitle(language: String): String {
        return when (language) {
            "hi" -> title.hi
            "en" -> title.en
            "bn" -> title.bn
            "te" -> title.te
            else -> title.en
        }
    }

}
