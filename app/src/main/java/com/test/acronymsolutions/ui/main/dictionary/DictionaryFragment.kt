package com.test.acronymsolutions.ui.main.dictionary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.acronymsolutions.R
import com.test.acronymsolutions.databinding.FragmentDictionaryBinding
import com.test.acronymsolutions.domain.dictionary.entity.LongFormEntity
import com.test.acronymsolutions.ui.common.extension.gone
import com.test.acronymsolutions.ui.common.extension.showToast
import com.test.acronymsolutions.ui.common.extension.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DictionaryFragment : Fragment(R.layout.fragment_dictionary) {

    private var _binding: FragmentDictionaryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeMainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDictionaryBinding.bind(view)
        setupRecyclerView()
        observe()

        binding.showButton.setOnClickListener {
            if (binding.acronymEditText.text.toString() != "") {
                viewModel.fetchDictionary(binding.acronymEditText.text.toString())
            }
        }
    }

    private fun setupRecyclerView(){
        val adapter = DisctionaryAdapter(mutableListOf())
        binding.productsRecyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    private fun observeState(){
        viewModel.mState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle,  Lifecycle.State.STARTED)
            .onEach { state ->
                handleState(state)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observeProducts(){
        viewModel.dictionary
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { products ->
                handleProducts(products)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun observe(){
        observeState()
        observeProducts()
    }

    private fun handleProducts(products: List<LongFormEntity>){
        binding.productsRecyclerView.adapter?.let {
            if(it is DisctionaryAdapter){
                it.updateList(products)
            }
        }
    }

    private fun handleState(state: DictionaryFragmentState){
        when(state){
            is DictionaryFragmentState.IsLoading -> handleLoading(state.isLoading)
            is DictionaryFragmentState.ShowToast -> requireActivity().showToast(state.message)
            is DictionaryFragmentState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading: Boolean){
        if(isLoading){
            binding.loadingProgressBar.visible()
        }else{
            binding.loadingProgressBar.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}