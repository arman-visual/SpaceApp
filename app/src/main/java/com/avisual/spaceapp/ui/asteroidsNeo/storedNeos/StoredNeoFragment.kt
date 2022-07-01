package com.avisual.spaceapp.ui.asteroidsNeo.storedNeos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.avisual.spaceapp.data.model.Neo
import com.avisual.spaceapp.databinding.NeoStoredFragmentBinding
import com.avisual.spaceapp.ui.asteroidsNeo.adapter.AsteroidsSavedAdapter
import com.avisual.spaceapp.ui.asteroidsNeo.storedNeos.viewModel.StoredNeoViewModel
import com.avisual.spaceapp.ui.common.toast
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoredNeoFragment : ScopeFragment() {

    private lateinit var binding: NeoStoredFragmentBinding
    private val viewModel: StoredNeoViewModel by viewModel()
    private lateinit var adapter: AsteroidsSavedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpUi()
        subscribe()
        return binding.root
    }

    private fun setUpUi() {
        adapter = AsteroidsSavedAdapter(emptyList(), onDeleteBtnClicked(), onClickedAsteroid())
        binding = NeoStoredFragmentBinding.inflate(layoutInflater)
        binding.recycler.adapter = adapter
    }

    private fun subscribe() {
        viewModel.asteroidsSaved.observe(viewLifecycleOwner) { neos ->
            if (!neos.isNullOrEmpty())
                adapter.setItems(neos)
            else
                requireActivity().toast("No hay Asteroides Almacenados")
        }
    }

    private fun onDeleteBtnClicked(): (Neo) -> Unit = { asteroid ->
        viewModel.removeAsteroidSaved(asteroid)
        requireActivity().toast("Asteroid with name: ${asteroid.name} is deleted")
    }

    private fun onClickedAsteroid(): (Neo) -> Unit = { asteroid ->
        val action =
            StoredNeoFragmentDirections.actionStoredNeoFragmentToDetailNeoFragment4(asteroid)
        findNavController().navigate(action)
    }
}