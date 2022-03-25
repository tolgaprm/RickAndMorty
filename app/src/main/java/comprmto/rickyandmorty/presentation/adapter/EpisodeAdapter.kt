package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.EpisodeItemRowBinding
import comprmto.rickyandmorty.domain.model.EpisodeDomain

class EpisodeAdapter : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    private var episodeList: List<EpisodeDomain> = emptyList()

    fun submitList(episodeList: List<EpisodeDomain>) {
        this.episodeList = episodeList
    }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {

        val episode = episodeList[position]
        holder.bind(episode)
    }

    override fun getItemCount(): Int {
        return episodeList.size
    }
}