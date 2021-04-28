package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetRoverPhotosByDate

class DetailRoverPhotoViewModelFactory constructor(
    private val getRoverPhotosByDate: GetRoverPhotosByDate
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailPhotoRoverViewModel(getRoverPhotosByDate) as T
}