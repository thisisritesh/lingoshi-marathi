package com.riteshmaagadh.lla_base.data.modals

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "topic")
data class Topic(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var lesson_name: String,
    var sublessonId: String,
    var index: Int,
    var info: Info?,
    var intro: Intro?,
    var layoutType: String,
    var options: Options?,
    var title: Title?,
    var translation: Translation?
) {

    constructor() : this(
        0,
        "",
        "",
        0,
        Info("", "", "", Info.Meaning("", "", "", ""), ""),
        Intro("", "", "", ""),
        "",
        Options(
            Options.Content("", ""),
            listOf(), 0
        ),
        Title("", "", "", ""),
        Translation("", Translation.DisplayText("", "", "", ""), listOf(), "")
    )

    data class BtnTxt(
        var hi: String,
        var en: String,
        var bn: String,
        var te: String
    ) {
        constructor() : this("", "", "", "")
    }

//    fun getBtnTxt(language: String): String {
//        return when (language) {
//            "hi" -> btnTxt.hi
//            "en" -> btnTxt.en
//            "bn" -> btnTxt.bn
//            "te" -> btnTxt.te
//            else -> btnTxt.en
//        }
//    }

    fun getIntro(language: String): String {
        return when (language) {
            "hi" -> intro?.hi ?: ""
            "en" -> intro?.en ?: ""
            "bn" -> intro?.bn ?: ""
            "te" -> intro?.te ?: ""
            else -> intro?.en ?: ""
        }
    }


    fun getTitle(language: String): String {
        return when (language) {
            "hi" -> title?.hi ?: ""
            "en" -> title?.en ?: ""
            "bn" -> title?.bn ?: ""
            "te" -> title?.te ?: ""
            else -> title?.en ?: ""
        }
    }

    data class Info(
        var audio: String,
        var content: String,
        var img: String,
        var meaning: Meaning,
        var speechTxt: String
    ) {

        constructor() : this("", "", "", Meaning("", "", "", ""), "")

        data class Meaning(
            var hi: String,
            var en: String,
            var bn: String,
            var te: String
        ) {
            constructor() : this("", "", "", "")
        }

        fun getMeaning(language: String): String {
            return when (language) {
                "hi" -> meaning.hi
                "en" -> meaning.en
                "bn" -> meaning.bn
                "te" -> meaning.te
                else -> meaning.en
            }
        }

    }

    data class Intro(
        var hi: String,
        var en: String,
        var bn: String,
        var te: String
    ) {
        constructor() : this("", "", "", "")
    }

    data class Options(
        var content: Content,
        var options: List<Option>,
        var rightCode: Int,
    ) {

        constructor() : this(Content("", ""), listOf(), 0)

        data class Content(
            var audio: String,
            var displayTxt: String
        ) {
            constructor() : this("", "")
        }

        data class Option(
            var code: Int,
            var displayText: DisplayText
        ) {

            constructor() : this(0, DisplayText("", "", "", ""))

            data class DisplayText(
                var hi: String,
                var en: String,
                var bn: String,
                var te: String
            ) {
                constructor() : this("", "", "", "")
            }

            fun getDisplayText(language: String): String {
                return when (language) {
                    "hi" -> displayText.hi
                    "en" -> displayText.en
                    "bn" -> displayText.bn
                    "te" -> displayText.te
                    else -> displayText.en
                }
            }


        }
    }

    data class Title(
        var hi: String,
        var en: String,
        var bn: String,
        var te: String
    ) {
        constructor() : this("", "", "", "")
    }

    data class Translation(
        var audio: String,
        var displayText: DisplayText,
        var options: List<Option>,
        var rightCode: String
    ) {

        constructor() : this("", DisplayText("", "", "", ""), listOf(), "")

        data class DisplayText(
            var hi: String,
            var en: String,
            var bn: String,
            var te: String
        ) {
            constructor() : this("", "", "", "")
        }

        fun getDisplayText(language: String): String {
            return when (language) {
                "hi" -> displayText.hi
                "en" -> displayText.en
                "bn" -> displayText.bn
                "te" -> displayText.te
                else -> displayText.en
            }
        }

        data class Option(
            var code: String,
            var displayText: String
        ) {
            constructor() : this("", "")
        }
    }

}
