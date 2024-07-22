package com.glacierpower.feature.location.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.LocationResultModel
import com.glacierpower.feature.databinding.LocationItemBinding

class LocationAdapter(private val locationListener: LocationListener) : PagingDataAdapter<LocationResultModel,
        LocationAdapter.LocationViewHolder>(diffUtilCallback) {

    class LocationViewHolder(
        val binding: LocationItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.binding.locationName.text = location?.name
        holder.binding.planet.text = location?.type
        holder.itemView.setOnClickListener {
            locationListener.getLocationById(location?.id!!)
        }
        Log.i("Location ViewHolder","Success")
    }

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<LocationResultModel>() {
            override fun areItemsTheSame(oldItem: LocationResultModel, newItem: LocationResultModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: LocationResultModel, newItem: LocationResultModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}