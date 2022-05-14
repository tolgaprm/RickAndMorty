package comprmto.rickyandmorty.presentation.location.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.CharacterItemRcwBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.util.ItemClickListener

class LocationDetailAdapter(
    private val onClickListener: ItemClickListener
) :
    ListAdapter<CharactersDomain, LocationDetailAdapter.LocationDetailViewHolder>(DiffUtilLocation()) {

    class LocationDetailViewHolder(val binding: CharacterItemRcwBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): LocationDetailViewHolder {
                val binding =
                    CharacterItemRcwBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return LocationDetailViewHolder(binding)
            }
        }

        fun bind(charactersDomain: CharactersDomain) {
            binding.characterModel = charactersDomain
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailViewHolder {
        return LocationDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocationDetailViewHolder, position: Int) {
        val charactersDomain = getItem(position)

        holder.bind(charactersDomain)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(charactersDomain.id)
        }
    }

}

class DiffUtilLocation() : DiffUtil.ItemCallback<CharactersDomain>() {
    override fun areItemsTheSame(
        oldItem: CharactersDomain,
        newItem: CharactersDomain
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersDomain,
        newItem: CharactersDomain
    ): Boolean {
        return oldItem == newItem
    }

}