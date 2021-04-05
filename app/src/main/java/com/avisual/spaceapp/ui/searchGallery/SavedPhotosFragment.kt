package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.R
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.FragmentSavedPhotosBinding
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.adapter.SavedPhotosAdapter
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModelFactory
import com.google.android.material.snackbar.Snackbar

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
        val callback = itemTouchHelperConfigure()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recycler)
    }


    private fun subscribeUi() {
        viewModel.saveLivePhotos.observe(requireActivity()) {
            photosAdapter.setItems(it)
        }
    }

    private fun itemTouchHelperConfigure() = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags, swipeFlags)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val photo = photosAdapter.getPhotoPosition(position)
            viewModel.deletePhoto(photo)
            Snackbar.make(binding.root, R.string.info, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo){viewModel.savePhoto(photo)}
                .show()
        }
    }

    private fun navToDetailPhoto(photo: PhotoGallery) {
        val action =
            SavedPhotosFragmentDirections.actionSavedPhotosFragmentToDetailPhotoGalleryFragment(
                photo
            )
        findNavController().navigate(action)
    }
}