package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetStoredNeos

class StoredNeoViewModelFactory constructor(
    private var getStoredNeos: GetStoredNeos
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        StoredNeoViewModel(getStoredNeos) as T
}