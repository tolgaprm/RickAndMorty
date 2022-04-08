package comprmto.rickyandmorty.presentation.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import comprmto.rickyandmorty.databinding.FragmentCharacterDetailBinding

import comprmto.rickyandmorty.presentation.adapter.EpisodeAdapter
import comprmto.rickyandmorty.presentation.character.viewmodel.CharacterDetailViewModel
import comprmto.rickyandmorty.util.NavigateState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val characterArgument: CharacterDetailFragmentArgs by navArgs()
    private lateinit var viewModel: CharacterDetailViewModel
    private val adapter: EpisodeAdapter by lazy { EpisodeAdapter() }

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterDetailBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val characterID = characterArgument.characterID
        val stateNavigate = characterArgument.stateNavigate
        val locationID = characterArgument.locationID
        val episodeID = characterArgument.episodeId

        viewModel.setCharacterId(characterID)
        viewModel.getCharacterInvoke()



        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEpisode.adapter = adapter


        binding.locationGroup.setOnClickListener {
            val locationUrl = viewModel.state.value.character?.location?.url



            locationUrl?.let {
                val locationId = (locationUrl.split("/"))[5].toInt()
                viewModel.setNavigateLocationId(locationId)


                if (viewModel.getNavigationLocationID() != null) {

                    navigateToLocationDetail(viewModel.getNavigationLocationID()!!)
                    viewModel.displayDetailComplete()
                }


            }

        }

        binding.imageButton.setOnClickListener {

            when (stateNavigate) {
                NavigateState.CHARACTERLIST -> navigateToCharacterList()
                NavigateState.EPISODEDETAIL -> navigateToEpisodeDetail(episodeID)
                else -> navigateToCharacterList()
            }


        }


        return binding.root
    }

    private fun navigateToEpisodeDetail(episodeID: Int) {
        val action = CharacterDetailFragmentDirections.actionToEpisodeDetailFragment(episodeID)
        findNavController().navigate(action)
    }

    private fun navigateToCharacterList() {
        val action =
            CharacterDetailFragmentDirections.actionCharacterDetailFragmentToCharacterListFragment()
        findNavController().navigate(action)
    }


    private fun navigateToLocationDetail(locationID: Int) {
        val action =
            CharacterDetailFragmentDirections.actionToLocationDetail(
                locationID,
                false
            )
        action.characterID = viewModel.state.value.characterIdFromCharacterListFragment

        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}