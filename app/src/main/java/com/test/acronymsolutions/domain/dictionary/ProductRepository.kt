package com.test.acronymsolutions.domain.dictionary

import com.test.acronymsolutions.data.common.utils.WrappedListResponse
import com.test.acronymsolutions.data.common.utils.WrappedResponse
import com.test.acronymsolutions.data.dictionary.remote.dto.DictionaryResponse
import com.test.acronymsolutions.domain.common.base.BaseResult
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getDictionary(acronym: String) : Flow<BaseResult<List<LongFormEntity>, WrappedListResponse<DictionaryResponse>>>
}