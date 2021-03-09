package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.FragmentDetailPhotoGalleryBinding
import com.avisual.spaceapp.model.nasaLibraryResponse.Item
import com.avisual.spaceapp.model.nasaLibraryResponse.convertToPhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModelFactory
import com.bumptech.glide.Glide


class DetailPhotoGalleryFragment : Fragment() {

    private val args: DetailPhotoGalleryFragmentArgs by navArgs()
    private lateinit var photo: Item
    private lateinit var viewModel: DetailPhotoViewModel
    private lateinit var photoGalleryRepository: PhotoGalleryRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailPhotoGalleryBinding.inflate(layoutInflater)
        photo = args.photoArg!!

        viewModel = buildViewModel()

        Glide.with(binding.root.context).load(photo.links[0].href).into(binding.imagePhoto)
        binding.titlePhotoDetail.text = photo.data_photo[0].title
        binding.descriptionPhotoDetail.text = photo.data_photo[0].description

        binding.btFavorite.setOnClickListener { viewModel.savePhoto(photo.convertToPhotoGallery()) }

        return binding.root
    }

    private fun buildViewModel(): DetailPhotoViewModel {
        val database = Db.getDatabase(requireContext())
        photoGalleryRepository = PhotoGalleryRepository(database)
        val factory = DetailPhotoViewModelFactory(photoGalleryRepository)
        return ViewModelProvider(this, factory).get(DetailPhotoViewModel::class.java)
    }
}