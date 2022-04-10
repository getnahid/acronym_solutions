package com.test.acronymsolutions.data.dictionary.remote.dto

import com.google.gson.annotations.SerializedName

data class DictionaryResponse(
    @SerializedName("sf") var sf: String,
    @SerializedName("lfs") var lfs: List<LongFormModel>
)