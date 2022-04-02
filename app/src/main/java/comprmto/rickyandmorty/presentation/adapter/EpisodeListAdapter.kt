package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.EpisodeItemRowBinding
import comprmto.rickyandmorty.domain.model.EpisodeDomain

class EpisodeListAdapter :
    PagingDataAdapter<EpisodeDomain, EpisodeListAdapter.EpisodeViewHolder>(DiffUtilEpisode()) {

    class EpisodeViewHolder(val binding: EpisodeItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val binding = EpisodeItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EpisodeViewHolder(binding)
            }
        }


        fun bind(episode: EpisodeDomain) {
            binding.episode = episode
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)

        holder.bind(episode!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent)
    }

    class DiffUtilEpisode : DiffUtil.ItemCallback<EpisodeDomain>() {
        override fun areItemsTheSame(oldItem: EpisodeDomain, newItem: EpisodeDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: EpisodeDomain, newItem: EpisodeDomain): Boolean {
            return oldItem == newItem
        }

    }
}