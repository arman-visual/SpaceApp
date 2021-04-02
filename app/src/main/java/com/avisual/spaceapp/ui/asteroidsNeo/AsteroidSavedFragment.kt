package com.avisual.spaceapp.ui.asteroidsNeo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.avisual.spaceapp.database.Db
import com.avisual.spaceapp.databinding.AsteroidSavedFragmentBinding
import com.avisual.spaceapp.model.Neo
import com.avisual.spaceapp.repository.NeoRepository
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsSavedAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.AsteroidSavedViewModel
import com.avisual.spaceapp.ui.asteroidsNeo.viewModel.AsteroidSavedViewModelFactory

class AsteroidSavedFragment : Fragment() {

    private lateinit var binding: AsteroidSavedFragmentBinding
    private lateinit var viewModel: AsteroidSavedViewModel
    private lateinit var adapter: AsteroidsSavedAdapter
    private lateinit var neoRepository: NeoRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        buildDependencies()
        adapter = AsteroidsSavedAdapter(emptyList()) { onClickItemButton(it) }
        binding = AsteroidSavedFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun onClickItemButton(asteroid: Neo) {
        viewModel.removeAsteroidSaved(asteroid)
    }

    private fun buildDependencies() {
        val database = Db.getDatabase(requireContext())
        neoRepository = NeoRepository(database)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = buildViewModel()
        subscribe()
    }

    private fun subscribe() {
        viewModel.asteroidsSaved.observe(requireActivity()) {
            adapter.setItems(it)
        }
    }

    private fun buildViewModel(): AsteroidSavedViewModel {
        val factory = AsteroidSavedViewModelFactory(neoRepository)
        return ViewModelProvider(this, factory).get(AsteroidSavedViewModel::class.java)
    }

}