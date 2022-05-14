package comprmto.rickyandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.CharacterItemRcwBinding
import comprmto.rickyandmorty.domain.CharactersDomain
import comprmto.rickyandmorty.presentation.character.viewmodel.states.ListType
import comprmto.rickyandmorty.util.ItemClickListener
import comprmto.rickyandmorty.util.ItemLongClickListener


class CharacterAdapter(
    private val onClickListener: ItemClickListener,
    private val onLongClickListener: ItemLongClickListener,
    private var listType: ListType = ListType.GridLayout
) :
    PagingDataAdapter<CharactersDomain, CharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {

        val characterModel = getItem(position)

        holder.bind(characterModel!!)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(characterModel.id)
        }

        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(characterModel)
            it == it
        }

        if (listType == ListType.LinearLayout) {
            holder.binding.characterImage.layoutParams.height = 500
            holder.binding.characterImage.requestLayout()
        } else {

            holder.binding.characterImage.layoutParams.height = 400
            holder.binding.characterImage.requestLayout()
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