package com.riteshmaagadh.lla_base.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riteshmaagadh.lla_base.data.modals.Lesson

@Entity("quick_learn")
data class QuickLearn(
    var index: Int,
    var ic_url: String,
    @PrimaryKey(autoGenerate = false)
    var doc_id: String,
    var disp_txt: DispTxt
) {

    constructor() : this(0,"","",QuickLearn.DispTxt("","","",""))

    data class DispTxt(
        var en: String,
        var hi: String,
        var bn: String,
        var te: String,
    ) {
        constructor() : this("","","","")
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