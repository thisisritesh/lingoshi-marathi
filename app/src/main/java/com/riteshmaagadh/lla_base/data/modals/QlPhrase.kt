package com.riteshmaagadh.lla_base.data.modals

import androidx.room.PrimaryKey
import com.riteshmaagadh.lla_base.data.entities.QuickLearn

data class QlPhrase(
    var audio_url: String,
    var index: Int,
    var speech_txt: String,
    var disp_txt: DispTxt
) {

    constructor() : this("",0,"", DispTxt("","","","",""))

    data class DispTxt(
        var en: String,
        var hi: String,
        var bn: String,
        var te: String,
        var mr: String,
    ) {
        constructor() : this("","","","","")
    }

    fun getDispTxt(language: String): String {
        return when (language) {
            "hi" -> disp_txt.hi
            "en" -> disp_txt.en
            "bn" -> disp_txt.bn
            "te" -> disp_txt.te
            else -> disp_txt.en
        }
    }
}
