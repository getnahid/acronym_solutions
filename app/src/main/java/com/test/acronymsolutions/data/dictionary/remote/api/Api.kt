package com.test.acronymsolutions.data.dictionary.remote.api

import com.test.acronymsolutions.data.common.utils.WrappedListResponse
import com.test.acronymsolutions.data.common.utils.WrappedResponse
import com.test.acronymsolutions.data.dictionary.remote.dto.DictionaryResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("dictionary.py")
    suspend fun getDescriptions(@Query("sf") acronym: String) : Response<List<DictionaryResponse>>
}