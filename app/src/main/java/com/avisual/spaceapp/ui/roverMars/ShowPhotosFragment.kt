package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.toast
import com.avisual.spaceapp.databinding.FragmentShowPhotosBinding
import com.avisual.spaceapp.model.PhotoRover
import com.avisual.spaceapp.repository.PhotoRoverRepository
import com.avisual.spaceapp.ui.roverMars.adapter.PhotosRoverAdapter
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosUi
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModelFactory

class ShowPhotosFragment : Fragment() {

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
        setupUi()
        subscribeUi()
        return binding.root
    }

    private fun buildDependencies() {
        photoRoverRepository = PhotoRoverRepository()
    }

    private fun buildViewModel(): ShowPhotosViewModel {
        val factory = ShowPhotosViewModelFactory(photoRoverRepository)
        return ViewModelProvider(this, factory).get(ShowPhotosViewModel::class.java)
    }

    private fun setupUi() {
        binding = FragmentShowPhotosBinding.inflate(layoutInflater)
        adapter = PhotosRoverAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = adapter
        binding.button.setOnClickListener { onClickSearchButton() }
    }

    private fun subscribeUi() {
        viewModel.model.observe(requireActivity(), Observer(::updateUi))
    }

    private fun updateUi(model: ShowPhotosUi) {
        binding.progressBarM.visibility =
            if (model is ShowPhotosUi.Loading) View.VISIBLE else View.GONE

        if (model is ShowPhotosUi.Content) {
            if (model.photos.isNotEmpty()) {
                adapter.setItems(model.photos)
            } else {
                adapter.setItems(model.photos)
                requireActivity().toast(getString(R.string.message_no_photos))
            }
        }
    }

    private fun onClickSearchButton() {

        if (validateInputs(
                binding.dday.text.toString(),
                binding.dmonth.text.toString(),
                binding.dyear.text.toString()
            )
        ) {
            requireActivity().toast(getString(R.string.message_input_error))
        } else {
            viewModel.findPhotosByDate(
                "${binding.dyear.text}-${binding.dmonth.text}-${binding.dday.text}",
                apiKey = getString(R.string.api_key)
            )
        }

    }

    private fun onClickPhoto(photo: PhotoRover) {
        val action =
            ShowPhotosFragmentDirections.actionShowPhotosFragmentToDetailPhotoRoverFragment(photo)
        findNavController().navigate(action)
    }

    private fun validateInputs(day: String, month: String, year: String): Boolean {
        return day.isEmpty() || month.isEmpty() || year.isEmpty()
    }
}