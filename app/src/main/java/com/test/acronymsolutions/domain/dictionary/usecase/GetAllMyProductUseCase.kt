package com.test.acronymsolutions.domain.dictionary.usecase

import com.test.acronymsolutions.data.common.utils.WrappedListResponse
import com.test.acronymsolutions.data.common.utils.WrappedResponse
import com.test.acronymsolutions.data.dictionary.remote.dto.DictionaryResponse
import com.test.acronymsolutions.domain.common.base.BaseResult
import com.test.acronymsolutions.domain.dictionary.ProductRepository
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMyProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun invoke(acronym: String) : Flow<BaseResult<List<LongFormEntity>, WrappedListResponse<DictionaryResponse>>> {
        return productRepository.getDictionary(acronym)
    }
}