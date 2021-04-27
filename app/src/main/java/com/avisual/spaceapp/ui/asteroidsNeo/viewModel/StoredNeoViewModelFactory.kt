package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetStoredNeos
import com.avisual.usecases.RemoveNeo

class StoredNeoViewModelFactory constructor(
    private var getStoredNeos: GetStoredNeos,
    private var removeNeo: RemoveNeo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        StoredNeoViewModel(getStoredNeos, removeNeo) as T
}