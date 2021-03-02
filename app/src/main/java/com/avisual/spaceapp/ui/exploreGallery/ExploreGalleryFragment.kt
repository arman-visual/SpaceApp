package com.avisual.spaceapp.ui.exploreGallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item

class ExploreGalleryFragment : Fragment() {

    private lateinit var binding: FragmentExploreGalleryBinding
    private lateinit var viewModel: ExploreGalleryViewModel
    private lateinit var photosAdapter: GalleryPhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("ExploreGalleryFragment", "Start this fragment")
        viewModel = buildViewModel()
        setUpUi()
        subscribeUi()
        return binding.root
    }

    private fun setUpUi() {
        binding = FragmentExploreGalleryBinding.inflate(layoutInflater)
        photosAdapter = GalleryPhotosAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = photosAdapter
    }

    private fun onClickPhoto(photo: Item) {
        Toast.makeText(requireActivity(),photo.data_photo[0].title,Toast.LENGTH_LONG).show()
    }

    private fun subscribeUi() {
        viewModel.photosLibrary.observe(requireActivity()) {
            photosAdapter.setItems(it)
        }
    }

    private fun buildViewModel(): ExploreGalleryViewModel =
        ViewModelProvider(this)[ExploreGalleryViewModel::class.java]
}