package com.avisual.spaceapp.ui.gallery.showGallery

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding
import com.avisual.spaceapp.ui.common.toast
import com.avisual.spaceapp.ui.gallery.adapter.GalleryPhotosAdapter
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel.*
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShowGalleryFragment : ScopeFragment() {

    private lateinit var binding: FragmentExploreGalleryBinding
    private val viewModel: ShowGalleryViewModel by viewModel()
    private lateinit var adapter: GalleryPhotosAdapter
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpUi()
        subscribeUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        //viewModel.refresh()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        configureSearchView(inflater, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpUi() {
        binding = FragmentExploreGalleryBinding.inflate(layoutInflater)
        adapter = GalleryPhotosAdapter(emptyList()) {
            onClickPhoto(it)
        }
        binding.recycler.adapter = adapter
    }

    private fun subscribeUi() {
        viewModel.model.observe(viewLifecycleOwner, Observer(::updateUi))
    }

    private fun updateUi(model: GalleryUi) {
        binding.progressBar.visibility =
            if (model is GalleryUi.Loading) View.VISIBLE else View.GONE

        if (model is GalleryUi.Content) {
            if (model.photos.isNotEmpty()) {
                adapter.setItems(model.photos.map { it.toGalleryFramework() })
            } else {
                adapter.setItems(model.photos.map { it.toGalleryFramework() })
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