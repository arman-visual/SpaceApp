package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.database.PhotoGallery
import com.avisual.spaceapp.databinding.FragmentSavedPhotosBinding
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.adapter.SavedPhotosAdapter
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModelFactory

class SavedPhotosFragment : Fragment() {

    private lateinit var binding: FragmentSavedPhotosBinding
    private lateinit var viewModel: SavedPhotosViewModel
    private lateinit var photoGalleryRepository: PhotoGalleryRepository
    private lateinit var photosAdapter: SavedPhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        viewModel = buildViewModel()
        setUpUi()
        subscribeUi()
        return binding.root
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        photoGalleryRepository = PhotoGalleryRepository(database)
    }

    private fun buildViewModel(): SavedPhotosViewModel {
        val factory = SavedPhotosViewModelFactory(photoGalleryRepository)
        return ViewModelProvider(this, factory).get(SavedPhotosViewModel::class.java)
    }

    private fun setUpUi() {
        binding = FragmentSavedPhotosBinding.inflate(layoutInflater)
        photosAdapter = SavedPhotosAdapter(emptyList()) {
            navToDetailPhoto(it)
        }
        binding.recycler.adapter = photosAdapter
    }

    private fun subscribeUi() {
        viewModel.saveLivePhotos.observe(requireActivity()) {
            photosAdapter.setItems(it)
        }
    }

    private fun navToDetailPhoto(photo: PhotoGallery) {
        val action = SavedPhotosFragmentDirections.actionSavedPhotosFragmentToDetailPhotoGalleryFragment(photo)
        findNavController().navigate(action)
    }
}