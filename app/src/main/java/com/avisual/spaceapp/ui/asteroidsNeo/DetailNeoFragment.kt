package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.databinding.DetailNeoFragmentBinding
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.DetailNeoViewModel
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailNeoFragment : ScopeFragment() {

    private val args: DetailNeoFragmentArgs by navArgs()
    private lateinit var neo: Neo
    private val viewModel: DetailNeoViewModel by viewModel()
    private lateinit var binding: DetailNeoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        neo = args.neoArg!!
        setupUi()
        subscribe()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkIfPhotoSaved(neo)
    }

    private fun setupUi() {
        binding = DetailNeoFragmentBinding.inflate(layoutInflater)

        binding.apply {
            nameNeo.text = neo.name
            minDiameter.text = neo.minDiameter.toString()
            maxDiameter.text = neo.maxDiameter.toString()
            relativeVelocity.text = neo.relativeVelocityHour
            absoulteMagnitud.text = neo.absoluteMagnitudeH.toString()
            missDistance.text = neo.missDistance
            if (neo.isPotentiallyHazardousAsteroid) {
                danger.setImageResource(R.drawable.danger_on)
            } else {
                danger.setImageResource(R.drawable.danger_off)
            }
            fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(this@DetailNeoFragment.neo) }
        }
    }

    private fun subscribe() {
        viewModel.statusDb.observe(requireActivity()) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }
}