package com.avisual.spaceapp.ui.searchGallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.R
import com.avisual.spaceapp.common.loadUrl
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.FragmentDetailPhotoGalleryBinding
import com.avisual.spaceapp.model.PhotoGallery
import com.avisual.spaceapp.repository.PhotoGalleryRepository
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModel
import com.avisual.spaceapp.ui.searchGallery.viewModel.DetailPhotoViewModelFactory


class DetailPhotoGalleryFragment : Fragment() {

    private val args: DetailPhotoGalleryFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPhotoGalleryBinding
    private lateinit var photo: PhotoGallery
    private lateinit var viewModel: DetailPhotoViewModel
    private lateinit var photoGalleryRepository: PhotoGalleryRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoArg!!
        buildDependencies()
        viewModel = buildViewModel()
        setUpUi()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUi()
        viewModel.checkIfPhotoSaved(photo)
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        photoGalleryRepository = PhotoGalleryRepository(database)
    }

    private fun buildViewModel(): DetailPhotoViewModel {
        val factory = DetailPhotoViewModelFactory(photoGalleryRepository)
        return ViewModelProvider(this, factory).get(DetailPhotoViewModel::class.java)
    }

    private fun setUpUi() {
        binding = FragmentDetailPhotoGalleryBinding.inflate(layoutInflater)
        binding.imagePhoto.loadUrl(photo.url)
        binding.titlePhotoDetail.text = photo.title
        binding.descriptionPhotoDetail.text = photo.description
        binding.fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(photo) }
    }

    private fun subscribeUi() {
        viewModel.statusFavorite.observe(requireActivity()) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }

}