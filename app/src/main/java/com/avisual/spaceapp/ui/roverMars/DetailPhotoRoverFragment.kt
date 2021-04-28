package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.data.repository.RoverRepository
import com.avisual.spaceapp.R
import com.avisual.spaceapp.ui.common.loadUrl
import com.avisual.spaceapp.databinding.FragmentDetailPhotoRoverBinding
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.data.server.ServerRoverDataSource
import com.avisual.spaceapp.ui.roverMars.viewModel.DetailPhotoRoverViewModel
import com.avisual.spaceapp.ui.roverMars.viewModel.DetailRoverPhotoViewModelFactory
import com.avisual.usecases.GetRoverPhotosByDate


class DetailPhotoRoverFragment : Fragment() {

    private val args: DetailPhotoRoverFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPhotoRoverBinding
    private lateinit var viewModel: DetailPhotoRoverViewModel
    private lateinit var roverRepository: RoverRepository
    private lateinit var photo: PhotoRover
    private lateinit var getRoverPhotosByDate: GetRoverPhotosByDate

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoRoverArgs!!
        buildDependencies()
        viewModel = buildViewModel()
        setUpUi()
        return binding.root
    }

    private fun buildDependencies() {
        val apiKey =  getString(R.string.api_key)
        val remote = ServerRoverDataSource()
        roverRepository = RoverRepository(remote,apiKey)
        getRoverPhotosByDate = GetRoverPhotosByDate(roverRepository)
    }

    private fun buildViewModel(): DetailPhotoRoverViewModel {
        val factory = DetailRoverPhotoViewModelFactory(getRoverPhotosByDate)
        return ViewModelProvider(this, factory).get(DetailPhotoRoverViewModel::class.java)
    }

    private fun setUpUi() {
        binding = FragmentDetailPhotoRoverBinding.inflate(layoutInflater)

        binding.apply {
            imagePhoto.loadUrl(photo.img_src)
            titlePhotoDetail.text = photo.full_name
            roverName.text = photo.rover_name
            roverStatus.text = photo.status
            earthDate.text = photo.earth_date
            launchDate.text = photo.launch_date
            laundingDate.text = photo.landing_date
        }
    }

}