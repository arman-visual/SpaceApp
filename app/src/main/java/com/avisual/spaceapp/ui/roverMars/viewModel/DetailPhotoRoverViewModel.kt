package com.avisual.spaceapp.ui.roverMars.viewModel

import com.avisual.spaceapp.common.ScopeViewModel
import com.avisual.spaceapp.repository.PhotoRoverRepository

class DetailPhotoRoverViewModel(var photoRoverRepository: PhotoRoverRepository) :
    ScopeViewModel() {


    fun findPhotosByDate(date: String, apiKey: String) {
        TODO()
    }
}

