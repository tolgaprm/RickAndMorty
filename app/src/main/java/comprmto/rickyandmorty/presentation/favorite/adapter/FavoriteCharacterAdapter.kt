package comprmto.rickyandmorty.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.CharacterItemFavListBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.adapter.DiffUtilCallBack

class FavoriteCharacterAdapter :
    ListAdapter<CharactersDomain, FavoriteCharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {


    class CharacterViewHolder(val binding: CharacterItemFavListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun create(parent: ViewGroup): CharacterViewHolder {
                val binding = CharacterItemFavListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CharacterViewHolder(binding)
            }
        }

        fun bind(charactersDomain: CharactersDomain) {
            binding.characterModel = charactersDomain
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val charactersDomain = getItem(position)
        holder.bind(charactersDomain)

        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.up_anim
        )
    }
}

