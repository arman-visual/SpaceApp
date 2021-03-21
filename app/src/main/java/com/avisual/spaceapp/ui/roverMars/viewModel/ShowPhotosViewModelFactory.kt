package com.avisual.spaceapp.ui.roverMars.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.repository.PhotoCuriosityRepository

class ShowPhotosViewModelFactory constructor(
    private val photoCuriosityRepository: PhotoCuriosityRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ShowPhotosViewModel(photoCuriosityRepository) as T
}