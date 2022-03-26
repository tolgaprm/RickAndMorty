package comprmto.rickyandmorty.presentation.character.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.FragmentCharacterListBinding
import comprmto.rickyandmorty.presentation.adapter.CharacterAdapter
import comprmto.rickyandmorty.presentation.character.fragments.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    val viewModel: CharacterViewModel by viewModels()
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)



        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel



        prepareCharacterAdapter()




        lifecycleScope.launch {
            viewModel.getListData()
                .collectLatest {
                    characterAdapter.submitData(it)
                }
        }




        return binding.root
    }

    private fun prepareCharacterAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.characterList.layoutManager = layoutManager


        characterAdapter = CharacterAdapter(
            CharacterAdapter.ItemClickListener {
                val action =
                    CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                        it
                    )
                findNavController().navigate(action)
            }
        )
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.characterList.adapter = characterAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.imageButton.setOnClickListener {
            val action = CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}