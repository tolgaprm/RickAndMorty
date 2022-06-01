package comprmto.rickyandmorty.presentation.episode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.R
import comprmto.rickyandmorty.databinding.EpisodeItemRowBinding
import comprmto.rickyandmorty.databinding.SeparatorItemViewBinding
import comprmto.rickyandmorty.domain.model.EpisodeDomain
import comprmto.rickyandmorty.domain.model.EpisodeListItem
import comprmto.rickyandmorty.presentation.episode.view.EpisodeListFragmentDirections
import comprmto.rickyandmorty.util.ItemClickListener
import timber.log.Timber

class EpisodeListAdapter() :
    PagingDataAdapter<EpisodeListItem, RecyclerView.ViewHolder>(DiffUtilEpisode()) {

    class EpisodeViewHolder(val binding: EpisodeItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.episode?.id?.let { episodeId ->
                    navigateToEpisodeDetail(episodeId, it)
                }
            }
        }

        private fun navigateToEpisodeDetail(episodeId: Int, view: View) {
            val direction = EpisodeListFragmentDirections.actionToEpisodeDetail(episodeId)
            view.findNavController().navigate(direction)
        }

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

    class SeparatorViewHolder(val binding: SeparatorItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): SeparatorViewHolder {
                val binding = SeparatorItemViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return SeparatorViewHolder(binding)
            }
        }

        fun bind(separatorText: String) {
            binding.separatorText.text = separatorText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.separator_item_view) {
            SeparatorViewHolder.from(parent)
        } else {
            EpisodeViewHolder.from(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EpisodeListItem.EpisodeItem -> R.layout.episode_item_row
            is EpisodeListItem.SeparatorItem -> R.layout.separator_item_view
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episodeModel = getItem(position)



        episodeModel.let { episodeListItem ->
            when (episodeListItem) {
                is EpisodeListItem.EpisodeItem -> {
                    (holder as EpisodeViewHolder).bind(episodeListItem.episode)
                }
                is EpisodeListItem.SeparatorItem -> (holder as SeparatorViewHolder).bind(
                    episodeListItem.season
                )
                else -> {
                    Timber.e("unknown type")
                }
            }
        }


    }

    class DiffUtilEpisode : DiffUtil.ItemCallback<EpisodeListItem>() {
        override fun areItemsTheSame(oldItem: EpisodeListItem, newItem: EpisodeListItem): Boolean {
            return (oldItem is EpisodeListItem.EpisodeItem && newItem is EpisodeListItem.EpisodeItem && oldItem.episode.id == newItem.episode.id) ||
                    (oldItem is EpisodeListItem.SeparatorItem && newItem is EpisodeListItem.SeparatorItem && oldItem.season == newItem.season)
        }

        override fun areContentsTheSame(
            oldItem: EpisodeListItem,
            newItem: EpisodeListItem
        ): Boolean {
            return oldItem == newItem
        }

    }


}