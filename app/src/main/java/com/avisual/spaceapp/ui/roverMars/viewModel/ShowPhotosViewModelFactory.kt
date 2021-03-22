package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.repository.PhotoRoverRepository

class ShowPhotosViewModelFactory constructor(
    private val photoRoverRepository: PhotoRoverRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ShowPhotosViewModel(photoRoverRepository) as T
}