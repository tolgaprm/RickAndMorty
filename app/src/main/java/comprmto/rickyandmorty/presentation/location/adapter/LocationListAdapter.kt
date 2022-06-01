package comprmto.rickyandmorty.presentation.location.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import comprmto.rickyandmorty.databinding.LocationItemRowBinding
import comprmto.rickyandmorty.domain.model.LocationDomain
import comprmto.rickyandmorty.presentation.location.view.LocationListFragmentDirections

class LocationListAdapter(
) :
    PagingDataAdapter<LocationDomain, LocationListAdapter.LocationViewHolder>(DiffUtilLocation()) {


    class LocationViewHolder(val binding: LocationItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.location?.id?.let { locationId ->
                    navigateToLocationDetail(locationId, it)
                }
            }
        }

        private fun navigateToLocationDetail(locationId: Int, view: View) {
            val direction = LocationListFragmentDirections.actionToLocationDetail(locationId, true)
            view.findNavController().navigate(direction)
        }

        companion object {
            fun from(parent: ViewGroup): LocationViewHolder {
                val binding = LocationItemRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return LocationViewHolder(binding)
            }
        }

        fun bind(location: LocationDomain) {
            binding.location = location
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

        val location = getItem(position)

        holder.bind(location!!)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder.from(parent)
    }


    class DiffUtilLocation : DiffUtil.ItemCallback<LocationDomain>() {
        override fun areItemsTheSame(oldItem: LocationDomain, newItem: LocationDomain): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: LocationDomain, newItem: LocationDomain): Boolean {
            return oldItem == newItem
        }

    }
}