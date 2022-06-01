package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.CharacterItemRcwBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.character.view.CharacterListFragmentDirections
import comprmto.rickyandmorty.presentation.character.viewmodel.states.ListType
import comprmto.rickyandmorty.presentation.favorite.adapter.FavoriteCharacterAdapter
import comprmto.rickyandmorty.util.ItemLongClickListener


const val GRID_LAYOUT = 0
const val LINEARLAYOUT = 1

class CharacterAdapter(
    private val onLongClickListener: ItemLongClickListener,
    private var listType: ListType = ListType.GridLayout
) :
    PagingDataAdapter<CharactersDomain, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    fun setListType(listType: ListType) {
        this.listType = listType
    }


    class CharacterViewHolder(val binding: CharacterItemRcwBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.characterModel?.id?.let { id ->
                    navigateToCharacterDetail(id, it)
                }
            }

        }

        private fun navigateToCharacterDetail(id: Int, view: View) {
            val direction =
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    id
                )
            view.findNavController().navigate(direction)
        }

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val binding = CharacterItemRcwBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return CharacterViewHolder(binding)
            }
        }

        fun bind(characterModel: CharactersDomain) {
            binding.characterModel = characterModel
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == GRID_LAYOUT) {
            CharacterViewHolder.from(parent)
        } else {
            FavoriteCharacterAdapter.CharacterViewHolder.create(parent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.GridLayout -> GRID_LAYOUT
            ListType.LinearLayout -> LINEARLAYOUT
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val characterModel = getItem(position)
        if (listType == ListType.GridLayout) {

            holder as CharacterViewHolder
            holder.bind(characterModel!!)

            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.scale_up
            )

        } else {
            holder as FavoriteCharacterAdapter.CharacterViewHolder
            holder.binding.setClickListener {
                holder.binding?.characterModel?.id?.let { characterId ->
                    holder.navigateToCharacterDetail(id = characterId, it, FROMCHARACTERLIST)
                }
            }
            holder.bind(characterModel!!)

            holder.itemView.animation = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.up_anim
            )
        }

        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(characterModel)
            it == it
        }
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<CharactersDomain>() {
    override fun areItemsTheSame(oldItem: CharactersDomain, newItem: CharactersDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersDomain, newItem: CharactersDomain): Boolean {
        return oldItem == newItem
    }

}

const val FROMCHARACTERLIST = "fromCharacterList"
const val FROMFAVORITELIST = "fromFavoriteList"