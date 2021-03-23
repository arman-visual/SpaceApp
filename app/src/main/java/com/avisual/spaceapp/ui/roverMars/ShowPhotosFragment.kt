package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.FragmentShowPhotosBinding
import com.avisual.spaceapp.repository.PhotoRoverRepository
import com.avisual.spaceapp.ui.roverMars.adapter.PhotosRoverAdapter
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosUi
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModelFactory

class ShowPhotosFragment : Fragment() {

    companion object {
        private const val DEFAULT_DATE_EARTH = "2012-08-06"
    }

    private lateinit var binding: FragmentShowPhotosBinding
    private lateinit var adapter: PhotosRoverAdapter
    private lateinit var viewModel: ShowPhotosViewModel
    private lateinit var photoRoverRepository: PhotoRoverRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        viewModel = buildViewModel()
        viewModel.findPhotosByDate(DEFAULT_DATE_EARTH, apiKey = getString(R.string.api_key))
        binding = FragmentShowPhotosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = PhotosRoverAdapter(emptyList())
        binding.recycler.adapter = adapter
        binding.button.setOnClickListener { onClickSearchButton() }
        subscribeUi()
    }

    private fun onClickSearchButton() {
        var inputDay = binding.dyear.text
        var inputMoth = binding.dmonth.text
        var inputYear = binding.dyear.text

        var inputDate = "$inputYear-$inputMoth-$inputDay"
        viewModel.findPhotosByDate(inputDate, apiKey = getString(R.string.api_key))
    }

    private fun buildDependencies() {
        photoRoverRepository = PhotoRoverRepository()
    }

    private fun buildViewModel(): ShowPhotosViewModel {
        val factory = ShowPhotosViewModelFactory(photoRoverRepository)
        return ViewModelProvider(this, factory).get(ShowPhotosViewModel::class.java)
    }

    private fun subscribeUi() {
        viewModel.model.observe(requireActivity(), Observer(::updateUi))
    }

    private fun updateUi(model: ShowPhotosUi) {
        binding.progressBarM.visibility =
            if (model is ShowPhotosUi.Loading) View.VISIBLE else View.GONE

        val photos = if (model is ShowPhotosUi.Content) model.photos else emptyList()
        adapter.setItems(photos)
    }
}