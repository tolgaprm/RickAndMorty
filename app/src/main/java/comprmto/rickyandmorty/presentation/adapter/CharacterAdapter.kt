package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.CharacterItemRcwBinding
import comprmto.rickyandmorty.domain.CharactersDomain

class CharacterAdapter(private val onClickListener: ItemClickListener) :
    PagingDataAdapter<CharactersDomain, CharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val characterModel = getItem(position)

        holder.bind(characterModel!!)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(characterModel.id)
        }

    }

    class ItemClickListener(val clickListener: (characterId: Int) -> Unit) {
        fun onClick(characterId: Int) = clickListener(characterId)

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