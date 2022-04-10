package com.test.acronymsolutions.data.dictionary.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.acronymsolutions.data.common.utils.WrappedListResponse
import com.test.acronymsolutions.data.dictionary.remote.api.Api
import com.test.acronymsolutions.data.dictionary.remote.dto.DictionaryResponse
import com.test.acronymsolutions.domain.common.BaseResult
import com.test.acronymsolutions.domain.dictionary.ProductRepository
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val api: Api) : ProductRepository {
    override suspend fun getDictionary(acronym: String): Flow<BaseResult<List<LongFormEntity>, WrappedListResponse<DictionaryResponse>>> {
        return flow {
            val response = api.getDescriptions(acronym)
            if (response.isSuccessful){
                val response = response.body()?.get(0)
                val lfs = mutableListOf<LongFormEntity>()
                response?.lfs?.forEach { dictionaryResponse ->
                    lfs.add(LongFormEntity(
                        dictionaryResponse.lf,
                        dictionaryResponse.freq
                    ))
                }
                emit(BaseResult.Success(lfs))
            }else{
                val type = object : TypeToken<WrappedListResponse<DictionaryResponse>>(){}.type
                val err = Gson().fromJson<WrappedListResponse<DictionaryResponse>>(response.errorBody()!!.charStream(), type)!!
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}