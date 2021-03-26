package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.databinding.ShowNeoFragmentBinding
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModel

class ShowNeoFragment : Fragment() {

    private lateinit var viewModel: ShowNeoViewModel
    private lateinit var binding: ShowNeoFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ShowNeoViewModel::class.java)
        binding = ShowNeoFragmentBinding.inflate(layoutInflater)
        binding.text.text = "NEO"
        return binding.root
    }

}