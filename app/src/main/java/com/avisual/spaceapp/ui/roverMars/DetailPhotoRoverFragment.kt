package com.avisual.spaceapp.ui.roverMars

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.FragmentDetailPhotoRoverBinding


class DetailPhotoRoverFragment : Fragment() {

    private lateinit var binding: FragmentDetailPhotoRoverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPhotoRoverBinding.inflate(layoutInflater)
        return binding.root
    }

}