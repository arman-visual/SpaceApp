package com.avisual.spaceapp.ui.roverMars.viewModel

import com.avisual.spaceapp.ui.common.ScopeViewModel
import com.avisual.usecases.GetRoverPhotosByDate

class DetailPhotoRoverViewModel(private val getRoverPhotosByDate: GetRoverPhotosByDate) :
    ScopeViewModel() {

    fun findPhotosByDate(date: String, apiKey: String) {
        TODO()
    }
}

