package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.adapter.GalleryPhotosAdapter
import com.avisual.spaceapp.ui.searchGallery.viewModel.ShowGalleryViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.ShowGalleryViewModelFactory

class ShowGalleryFragment : Fragment() {

    private lateinit var binding: FragmentExploreGalleryBinding
    private lateinit var viewModel: ShowGalleryViewModel
    private lateinit var photosAdapter: GalleryPhotosAdapter
    private lateinit var navController: NavController
    private lateinit var menuNav: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_nav_gallery, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpUi() {
        binding = FragmentExploreGalleryBinding.inflate(layoutInflater)
        photosAdapter = GalleryPhotosAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = photosAdapter
    }

    private fun onClickPhoto(photo: Item) {
        Toast.makeText(requireActivity(), photo.data_photo[0].title, Toast.LENGTH_LONG).show()
        val action = ShowGalleryFragmentDirections
            .actionExploreGalleryFragmentToDetailPhotoGalleryFragment(photo)
        findNavController().navigate(action)
    }

    private fun subscribeUi() {
        viewModel.photosLibrary.observe(requireActivity()) {
            photosAdapter.setItems(it)
        }
    }

    private fun buildViewModel(): ShowGalleryViewModel {
        val factory = ShowGalleryViewModelFactory(PhotoGalleryRepository())
        return ViewModelProvider(this, factory).get(ShowGalleryViewModel::class.java)
    }
}