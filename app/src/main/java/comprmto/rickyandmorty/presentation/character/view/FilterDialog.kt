package comprmto.rickyandmorty.presentation.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import comprmto.rickyandmorty.databinding.DialogFilterBinding
import comprmto.rickyandmorty.presentation.character.viewmodel.CharacterViewModel
import comprmto.rickyandmorty.util.GenderState
import comprmto.rickyandmorty.util.StatusState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FilterDialog : BottomSheetDialogFragment() {

    private lateinit var binding: DialogFilterBinding
    lateinit var viewModel: CharacterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DialogFilterBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        var statusState: StatusState = StatusState.NONE
        var genderState: GenderState = GenderState.NONE


        lifecycleScope.launch {
            viewModel.state.collect {
                statusState = it.statusState
                genderState = it.genderState

                updateRadioGroupStatusThatLastData(statusState)
                updateRadioGroupGenderThatLastData(genderState)

                if (statusState != StatusState.ALIVE || genderState != GenderState.FEMALE) {
                    binding.clear.visibility = View.VISIBLE
                } else {
                    binding.clear.visibility = View.GONE
                }
            }


        }

        binding.clear.setOnClickListener {
            viewModel.setStatusState(StatusState.NONE)
            viewModel.setGenderState(GenderState.NONE)
            binding.characterName.setText("")
        }

        binding.rgStatus.setOnCheckedChangeListener { radioGroup, idThatSelected ->
            viewModel.setStatusState(
                when (idThatSelected) {
                    binding.rbAlive.id -> StatusState.ALIVE
                    binding.rbDead.id -> StatusState.DEAD
                    binding.rbUnknown.id -> StatusState.UNKNOWN
                    else -> StatusState.NONE
                }
            )
        }

        binding.rgGender.setOnCheckedChangeListener { radioGroup, idThatSelected ->
            viewModel.setGenderState(
                when (idThatSelected) {
                    binding.rbFemale.id -> GenderState.FEMALE
                    binding.rbMale.id -> GenderState.MALE
                    binding.rbgUnknown.id -> GenderState.UNKNOWN
                    binding.rbGenderless.id -> GenderState.GENDERLESS
                    else -> GenderState.NONE
                }
            )
        }

        binding.btnApply.setOnClickListener {
            val action = FilterDialogDirections.actionFilterDialogToCharacterListFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateRadioGroupGenderThatLastData(genderState: GenderState) {

        if (genderState == GenderState.NONE) {
            binding.rgGender.clearCheck()
        } else {
            binding.rgGender.check(
                when (genderState) {
                    GenderState.MALE -> binding.rbMale.id
                    GenderState.FEMALE -> binding.rbFemale.id
                    GenderState.GENDERLESS -> binding.rbGenderless.id
                    else -> binding.rbgUnknown.id

                }
            )
        }


    }

    private fun updateRadioGroupStatusThatLastData(statusState: StatusState) {

        if (statusState == StatusState.NONE) {
            binding.rgStatus.clearCheck()
        } else {
            binding.rgStatus.check(
                when (statusState) {
                    StatusState.ALIVE -> binding.rbAlive.id
                    StatusState.DEAD -> binding.rbDead.id
                    else -> binding.rbUnknown.id
                }
            )
        }

    }
}