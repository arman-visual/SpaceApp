package com.avisual.spaceapp.ui.gallery.savedPhoto

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.PhotoGallery
import com.avisual.spaceapp.databinding.FragmentSavedPhotosBinding
import com.avisual.spaceapp.ui.gallery.adapter.SavedPhotosAdapter
import com.avisual.spaceapp.ui.gallery.savedPhoto.viewModel.SavedPhotosViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedPhotosFragment : Fragment() {

    private var _binding: FragmentSavedPhotosBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SavedPhotosViewModel by viewModel()
    private lateinit var photosAdapter: SavedPhotosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        subscribeUi()
    }

    private fun setUpUi() {
        photosAdapter = SavedPhotosAdapter(emptyList()) {
            navToDetailPhoto(it)
        }
        binding.recycler.adapter = photosAdapter
        val callback = itemTouchHelperConfigure()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recycler)
    }

    private fun subscribeUi() {
        viewModel.modelSavedPhotos.observe(viewLifecycleOwner) { model ->
            when (model) {
                is SavedPhotosViewModel.SavedPhotosUi.Content -> {
                    if (!model.storedPhotos.isNullOrEmpty())
                        photosAdapter.setItems(model.storedPhotos!!)
                    else
                        Log.e("SavedPhotosFragment", "No hay fotos guardadas")
                }
            }

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}