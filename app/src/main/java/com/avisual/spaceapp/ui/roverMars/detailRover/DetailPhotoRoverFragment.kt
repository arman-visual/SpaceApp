package com.avisual.spaceapp.ui.roverMars.detailRover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.data.model.PhotoRover
import com.avisual.spaceapp.databinding.FragmentDetailPhotoRoverBinding
import com.avisual.spaceapp.ui.common.loadUrl


class DetailPhotoRoverFragment : Fragment() {

    private val args: DetailPhotoRoverFragmentArgs by navArgs()
    private var _binding: FragmentDetailPhotoRoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var photo: PhotoRover

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        photo = args.photoRoverArgs!!
        _binding = FragmentDetailPhotoRoverBinding.inflate(inflater, container, false)
        setUpUi()
        return binding.root
    }

    private fun setUpUi() {

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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}