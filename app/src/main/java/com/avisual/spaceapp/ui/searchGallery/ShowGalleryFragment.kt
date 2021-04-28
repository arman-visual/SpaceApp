package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.data.repository.GalleryRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.toast
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.server.ServerGalleryDataSource
import com.avisual.spaceapp.ui.searchGallery.adapter.GalleryPhotosAdapter
import com.avisual.spaceapp.ui.searchGallery.viewModel.GalleryUi
import com.avisual.spaceapp.ui.searchGallery.viewModel.ShowGalleryViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.ShowGalleryViewModelFactory
import com.avisual.usecases.GetGalleryPhotosByKeyword

class ShowGalleryFragment : Fragment() {

    private lateinit var binding: FragmentExploreGalleryBinding
    private lateinit var viewModel: ShowGalleryViewModel
    private lateinit var adapter: GalleryPhotosAdapter
    private lateinit var navController: NavController
    private lateinit var galleryRepository: GalleryRepository
    private lateinit var getGalleryPhotosByKeyword: GetGalleryPhotosByKeyword

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        configureSearchView(inflater, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun buildDependencies() {
        val remote = ServerGalleryDataSource()
        galleryRepository = GalleryRepository(remote)
        getGalleryPhotosByKeyword = GetGalleryPhotosByKeyword(galleryRepository)
    }

    private fun buildViewModel(): ShowGalleryViewModel {
        val factory = ShowGalleryViewModelFactory(getGalleryPhotosByKeyword)
        return ViewModelProvider(this, factory).get(ShowGalleryViewModel::class.java)
    }

    private fun setUpUi() {
        binding = FragmentExploreGalleryBinding.inflate(layoutInflater)
        adapter = GalleryPhotosAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.photos.observe(requireActivity(), Observer(::updateUi))
    }

    private fun updateUi(model: GalleryUi) {
        binding.progressBar.visibility =
            if (model is GalleryUi.Loading) View.VISIBLE else View.GONE

        if (model is GalleryUi.Content) {
            if (model.photos.isNotEmpty()) {
                adapter.setItems(model.photos)
            } else {
                adapter.setItems(model.photos)
                requireActivity().toast(getString(R.string.message_no_found_photos))
            }
        }
    }

    private fun configureSearchView(inflater: MenuInflater, menu: Menu) {
        inflater.inflate(R.menu.menu_nav_gallery, menu)
        val searchItem = menu.findItem(R.id.nav_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search photos of NASA..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword != null) viewModel.findPhotosByKeyword(keyword)
                else Toast.makeText(
                    requireActivity(),
                    "You have insert a word!",
                    Toast.LENGTH_LONG
                )
                    .show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


    private fun onClickPhoto(photo: PhotoGallery) {
        val action = ShowGalleryFragmentDirections
            .actionExploreGalleryFragmentToDetailPhotoGalleryFragment(photo)
        findNavController().navigate(action)
    }
}