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
import com.avisual.data.repository.GalleryRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.database.Db
import com.avisual.spaceapp.data.database.RoomGalleryDataSource
import com.avisual.spaceapp.databinding.FragmentSavedPhotosBinding
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.data.server.ServerGalleryDataSource
import com.avisual.spaceapp.ui.searchGallery.adapter.SavedPhotosAdapter
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.SavedPhotosViewModelFactory
import com.avisual.usecases.DeleteGalleryPhoto
import com.avisual.usecases.GetAllStoredPhotos
import com.avisual.usecases.SaveGalleryPhoto
import com.google.android.material.snackbar.Snackbar

class SavedPhotosFragment : Fragment() {

    private lateinit var binding: FragmentSavedPhotosBinding
    private lateinit var viewModel: SavedPhotosViewModel
    private lateinit var photosAdapter: SavedPhotosAdapter
    private lateinit var galleryRepository: GalleryRepository
    private lateinit var saveGalleryPhoto: SaveGalleryPhoto
    private lateinit var deleteGalleryPhoto: DeleteGalleryPhoto
    private lateinit var getAllStoredPhotos: GetAllStoredPhotos

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
        val remote = ServerGalleryDataSource()
        val local = RoomGalleryDataSource(database)
        galleryRepository = GalleryRepository(remote, local)
        saveGalleryPhoto = SaveGalleryPhoto(galleryRepository)
        deleteGalleryPhoto = DeleteGalleryPhoto(galleryRepository)
        getAllStoredPhotos = GetAllStoredPhotos(galleryRepository)
    }

    private fun buildViewModel(): SavedPhotosViewModel {
        val factory =
            SavedPhotosViewModelFactory(saveGalleryPhoto, deleteGalleryPhoto, getAllStoredPhotos)
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
        viewModel.storedPhotos.observe(requireActivity()) {
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
                .setAction(R.string.undo) { viewModel.savePhoto(photo) }
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