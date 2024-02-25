package com.riteshmaagadh.lla_base.data.modals

import com.google.gson.annotations.SerializedName


data class Sublesson(
    @SerializedName("topics")
    var topics: List<Topic>
) {
    constructor() : this(mutableListOf())
}
