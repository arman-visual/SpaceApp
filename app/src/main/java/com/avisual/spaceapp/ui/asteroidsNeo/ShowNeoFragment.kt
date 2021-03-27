package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.R
import com.avisual.spaceapp.databinding.ShowNeoFragmentBinding
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.ShowNeoViewModelFactory
import com.avisual.spaceapp.ui.roverMars.viewModel.ShowPhotosViewModel

class ShowNeoFragment : Fragment() {

    private lateinit var viewModel: ShowNeoViewModel
    private lateinit var binding: ShowNeoFragmentBinding
    private lateinit var neoRepository: NeoRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dependencies()
        viewModel = buildViewModel()
        binding = ShowNeoFragmentBinding.inflate(layoutInflater)
        binding.text.text = "NEO"
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAsteroidsByDate("2021-02-21", "2021-02-27", getString(R.string.api_key))
    }

    private fun dependencies() {
        neoRepository = NeoRepository()
    }

    private fun buildViewModel(): ShowNeoViewModel {
        val factory = ShowNeoViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(ShowNeoViewModel::class.java)
    }


}