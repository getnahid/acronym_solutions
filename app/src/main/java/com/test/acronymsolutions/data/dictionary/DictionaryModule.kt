package com.test.acronymsolutions.data.dictionary

import com.test.acronymsolutions.data.common.module.NetworkModule
import com.test.acronymsolutions.data.dictionary.remote.api.Api
import com.test.acronymsolutions.data.dictionary.repository.DictionaryRepositoryImpl
import com.test.acronymsolutions.domain.dictionary.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class DictionaryModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit) : Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(api: Api) : ProductRepository {
        return DictionaryRepositoryImpl(api)
    }
}