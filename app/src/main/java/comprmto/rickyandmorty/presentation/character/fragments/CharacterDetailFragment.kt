package comprmto.rickyandmorty.presentation.character.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import comprmto.rickyandmorty.databinding.FragmentCharacterDetailBinding
import comprmto.rickyandmorty.presentation.adapter.EpisodeAdapter
import comprmto.rickyandmorty.presentation.character.fragments.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private val characterArgument: CharacterDetailFragmentArgs by navArgs()
    private val viewModel: CharacterViewModel by activityViewModels()
    private lateinit var adapter: EpisodeAdapter

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterDetailBinding.inflate(layoutInflater, container, false)


        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val characterID = characterArgument.characterID
        viewModel.setCharacterId(characterID)
        viewModel.getCharacterInvoke()
        viewModel.getEpisodesByCharacters()

        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(requireContext())
        adapter = EpisodeAdapter()
        adapter.submitList(viewModel.state.value.episodeInfoList)
        binding.recyclerViewEpisode.adapter = adapter


        binding.imageButton.setOnClickListener {
            val action =
                CharacterDetailFragmentDirections.actionCharacterDetailFragmentToCharacterListFragment()
            findNavController().navigate(action)
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}