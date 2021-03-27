package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.repository.NeoRepository

class ShowNeoViewModelFactory constructor(
    private var neoRepository: NeoRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ShowNeoViewModel(neoRepository) as T
}