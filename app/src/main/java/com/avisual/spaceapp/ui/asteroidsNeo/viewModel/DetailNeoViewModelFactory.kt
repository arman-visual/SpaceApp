package com.avisual.spaceapp.ui.asteroidsNeo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.avisual.usecases.GetNeoById
import com.avisual.usecases.RemoveNeo
import com.avisual.usecases.SaveNeoInDb

class DetailNeoViewModelFactory constructor(
    private var saveNeoInDb: SaveNeoInDb,
    private var getNeoById: GetNeoById,
    private var removeNeo: RemoveNeo
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        DetailNeoViewModel(saveNeoInDb, getNeoById, removeNeo) as T
}