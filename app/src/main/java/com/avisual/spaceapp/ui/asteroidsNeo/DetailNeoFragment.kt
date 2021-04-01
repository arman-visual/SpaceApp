package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.databinding.DetailNeoFragmentBinding
import com.avisual.spaceapp.model.Neo

class DetailNeoFragment : Fragment() {

    private val args: DetailNeoFragmentArgs by navArgs()
    private lateinit var neo: Neo

    companion object {
        fun newInstance() = DetailNeoFragment()
    }

    private lateinit var viewModel: DetailNeoViewModel
    private lateinit var binding: DetailNeoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        neo = args.neoArg!!
        binding = DetailNeoFragmentBinding.inflate(layoutInflater)
        setupUi()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailNeoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setupUi() {
        binding.apply {
            nameNeo.text = neo.name
        }
    }
}