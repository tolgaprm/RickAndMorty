package comprmto.rickyandmorty.presentation.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import comprmto.rickyandmorty.databinding.FragmentLocationDetailBinding
import comprmto.rickyandmorty.presentation.location.adapter.LocationDetailAdapter
import comprmto.rickyandmorty.presentation.location.viewModel.LocationDetailViewModel
import comprmto.rickyandmorty.util.ItemClickListener
import comprmto.rickyandmorty.util.NavigateState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    private val binding get() = _binding!!
    private val locationArgs: LocationDetailFragmentArgs by navArgs()
    private val viewModel: LocationDetailViewModel by viewModels()
    private lateinit var adapter: LocationDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)


        val locationID = locationArgs.locationId
        val isFromNavigateLocationList = locationArgs.isFromLocationList
        viewModel.setLocationID(locationID)
        viewModel.getLocationInfo()

        prepareAdapter(locationID)



        lifecycleScope.launch {
            viewModel.state.collectLatest {
                binding.viewModel = viewModel
            }
        }

        binding.imageButton.setOnClickListener {

            if (isFromNavigateLocationList) {
                navigateToLocationList()
            } else {
                navigateToCharacterDetail()
            }

        }

        return binding.root
    }

    private fun navigateToLocationList() {
        val action =
            LocationDetailFragmentDirections.actionToLocationList()

        findNavController().navigate(action)
    }

    private fun navigateToCharacterDetail() {
        val action =
            LocationDetailFragmentDirections.actionToCharacterDetail(
                locationArgs.characterID,
                NavigateState.LOCATIONDETAIL
            )

        findNavController().navigate(action)
    }

    private fun prepareAdapter(locationID: Int) {
        adapter = LocationDetailAdapter(
            ItemClickListener {characterId->
                val action =
                    LocationDetailFragmentDirections.actionToCharacterDetail(
                        characterId,
                        NavigateState.LOCATIONDETAIL
                    )
                action.locationID = locationID
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}