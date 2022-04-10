package com.test.acronymsolutions.ui.main.dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.acronymsolutions.domain.common.BaseResult
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity
import com.test.acronymsolutions.domain.dictionary.usecase.GetAllMyProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(private val getAllMyProductUseCase: GetAllMyProductUseCase) : ViewModel(){
    private val state = MutableStateFlow<DictionaryFragmentState>(DictionaryFragmentState.Init)
    val mState: StateFlow<DictionaryFragmentState> get() = state

    private val products = MutableStateFlow<List<LongFormEntity>>(mutableListOf())
    val dictionary: StateFlow<List<LongFormEntity>> get() = products

    private fun setLoading(){
        state.value = DictionaryFragmentState.IsLoading(true)
    }

    private fun hideLoading(){
        state.value = DictionaryFragmentState.IsLoading(false)
    }

    private fun showToast(message: String){
        state.value = DictionaryFragmentState.ShowToast(message)
    }

    fun fetchDictionary(acronym: String){
        viewModelScope.launch {
            getAllMyProductUseCase.invoke(acronym)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success -> {
                            products.value = result.data
                        }
                        is BaseResult.Error -> {
                            showToast(result.rawResponse.message)
                        }
                    }
                }
        }
    }
}

sealed class DictionaryFragmentState {
    object Init : DictionaryFragmentState()
    data class IsLoading(val isLoading: Boolean) : DictionaryFragmentState()
    data class ShowToast(val message : String) : DictionaryFragmentState()
}