package com.test.acronymsolutions.data.dictionary.remote.dto

import com.google.gson.annotations.SerializedName

data class LongFormModel(
    @SerializedName("lf") var lf: String,
    @SerializedName("freq") var freq: Int,
)