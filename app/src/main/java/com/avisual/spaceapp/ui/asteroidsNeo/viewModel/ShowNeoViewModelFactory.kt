package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetAllNeoByDate

class ShowNeoViewModelFactory constructor(
    private var getAllNeoByDate: GetAllNeoByDate
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ShowNeoViewModel(getAllNeoByDate) as T
}