package comprmto.rickyandmorty.presentation.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.FragmentCharacterListBinding
import comprmto.rickyandmorty.presentation.adapter.CharacterAdapter
import comprmto.rickyandmorty.presentation.character.viewmodel.CharacterViewModel
import comprmto.rickyandmorty.util.ItemClickListener
import comprmto.rickyandmorty.util.NavigateState
import comprmto.rickyandmorty.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    lateinit var viewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        prepareCharacterAdapter()

        viewModel.checkIfTheFilterHasBeenApplied()

        getListData()

        lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.refreshBtn,
                    isListEmpty,
                    binding.filterErrorMessage,
                    viewModel.checkIfTheFilterHasBeenApplied()
                )
            }
        }

        binding.refreshBtn.setOnClickListener {
            characterAdapter.retry()
        }



        return binding.root
    }


    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getListData().collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun prepareCharacterAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.characterList.layoutManager = layoutManager


        characterAdapter = CharacterAdapter(
            ItemClickListener {
                val action =
                    CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                        it,
                        NavigateState.CHARACTERLIST
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

}