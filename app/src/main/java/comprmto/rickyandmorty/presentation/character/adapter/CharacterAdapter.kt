package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.CharacterItemRcwBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.character.viewmodel.states.ListType
import comprmto.rickyandmorty.presentation.favorite.adapter.FavoriteCharacterAdapter
import comprmto.rickyandmorty.util.ItemClickListener
import comprmto.rickyandmorty.util.ItemLongClickListener


class CharacterAdapter(
    private val onClickListener: ItemClickListener,
    private val onLongClickListener: ItemLongClickListener,
    private var listType: ListType = ListType.GridLayout
) :
    PagingDataAdapter<CharactersDomain, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    fun setListType(listType: ListType) {
        this.listType = listType
    }


    class CharacterViewHolder(val binding: CharacterItemRcwBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
        return if (viewType == R.layout.character_item_rcw) {
            CharacterViewHolder.from(parent)
        } else {
            FavoriteCharacterAdapter.CharacterViewHolder.create(parent)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (listType) {
            ListType.GridLayout -> R.layout.character_item_rcw
            ListType.LinearLayout -> R.layout.character_item_fav_list
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val characterModel = getItem(position)
        if (listType == ListType.GridLayout) {

            holder as CharacterViewHolder
            holder.bind(characterModel!!)

        } else {
            holder as FavoriteCharacterAdapter.CharacterViewHolder
            holder.bind(characterModel!!)
        }

        holder.itemView.setOnClickListener {
            onClickListener.onClick(characterModel.id)
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