package com.avisual.spaceapp.ui.gallery.showGallery

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.toGalleryFramework
import com.avisual.spaceapp.databinding.FragmentExploreGalleryBinding
import com.avisual.spaceapp.ui.common.checkInternetConnection
import com.avisual.spaceapp.ui.common.toast
import com.avisual.spaceapp.ui.gallery.adapter.GalleryPhotosAdapter
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel
import com.avisual.spaceapp.ui.gallery.showGallery.viewModel.ShowGalleryViewModel.GalleryUi
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShowGalleryFragment : Fragment() {

    private var _binding: FragmentExploreGalleryBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentExploreGalleryBinding.inflate(inflater, container, false)
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
        adapter = GalleryPhotosAdapter(emptyList()) { onClickPhoto(it) }
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
                binding.recycler.visibility = View.VISIBLE
                binding.etInfoResult.visibility = View.GONE
                adapter.setItems(model.photos.map { it.toGalleryFramework() })
            } else {
                binding.etInfoResult.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
                binding.etInfoResult.text = getString(R.string.message_no_found_photos)
            }
        }
    }

    private fun configureSearchView(inflater: MenuInflater, menu: Menu) {
        inflater.inflate(R.menu.menu_nav_gallery, menu)
        val searchItem = menu.findItem(R.id.nav_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.label_search_nasa)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (requireActivity().checkInternetConnection(Context.CONNECTIVITY_SERVICE)) {
                    if (keyword != null) viewModel.findPhotosByKeyword(
                        keyword
                    )
                    else Toast.makeText(
                        requireActivity(),
                        getString(R.string.label_require_field),
                        Toast.LENGTH_LONG
                    )
                        .show()
                } else
                    requireActivity().toast(getString(R.string.label_check_connection))
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}