package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.databinding.FragmentDetailPhotoRoverBinding
import com.avisual.spaceapp.ui.common.loadUrl
import com.avisual.spaceapp.ui.roverMars.viewModel.DetailPhotoRoverViewModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailPhotoRoverFragment : ScopeFragment() {

    private val args: DetailPhotoRoverFragmentArgs by navArgs()
    private lateinit var binding: FragmentDetailPhotoRoverBinding
    private val viewModel: DetailPhotoRoverViewModel by viewModel()
    private lateinit var photo: PhotoRover

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoRoverArgs!!
        setUpUi()
        return binding.root
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