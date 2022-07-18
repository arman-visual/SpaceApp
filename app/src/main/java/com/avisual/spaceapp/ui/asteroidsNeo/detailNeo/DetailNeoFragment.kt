package com.avisual.spaceapp.ui.asteroidsNeo.detailNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.avisual.spaceapp.R
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.databinding.DetailNeoFragmentBinding
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailNeoFragment : Fragment() {

    private val args: DetailNeoFragmentArgs by navArgs()
    private lateinit var neo: Neo
    private val viewModel: DetailNeoViewModel by viewModel()
    private var _binding: DetailNeoFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        neo = args.neoArg!!
        _binding = DetailNeoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        subscribe()
        viewModel.checkIfPhotoSaved(neo)
    }

    private fun setupUi() {
        binding.apply {
            nameNeo.text = neo.name
            minDiameter.text = neo.minDiameter.toString()
            maxDiameter.text = neo.maxDiameter.toString()
            relativeVelocity.text = neo.relativeVelocityHour
            absoulteMagnitud.text = neo.absoluteMagnitudeH.toString()
            imageNeo.setImageResource(R.drawable.asteroid)
            missDistance.text = neo.missDistance
            approachDate.text = neo.approachDateFull
            if (neo.isPotentiallyHazardousAsteroid) {
                danger.setImageResource(R.drawable.danger_on)
            } else {
                danger.setImageResource(R.drawable.danger_off)
            }
            fbtSaveFavorite.setOnClickListener { viewModel.changeSaveStatusOfPhoto(this@DetailNeoFragment.neo) }
        }
    }

    private fun subscribe() {
        viewModel.statusDb.observe(viewLifecycleOwner) { isSaved ->
            val drawableRes = if (isSaved) {
                R.drawable.photo_saved
            } else {
                R.drawable.photo_no_saved
            }
            binding.fbtSaveFavorite.setImageResource(drawableRes)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}