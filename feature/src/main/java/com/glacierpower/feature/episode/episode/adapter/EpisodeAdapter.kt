package com.glacierpower.feature.episode.episode.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.feature.databinding.EpisodeItemBinding

class EpisodeAdapter (private val episodeListener: EpisodeListener) : PagingDataAdapter<EpisodeModel,
        EpisodeAdapter.EpisodeViewHolder>(diffUtilCallback) {

    class EpisodeViewHolder(
        val binding: EpisodeItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return EpisodeViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = getItem(position)
        holder.binding.episodeName.text = episode?.name
        holder.binding.episode.text = episode?.episode
        holder.binding.airDate.text = episode?.air_date
        holder.itemView.setOnClickListener {
            episodeListener.getEpisodeById(episode?.id!!)
        }
    }

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<EpisodeModel>() {
            override fun areItemsTheSame(oldItem: EpisodeModel, newItem: EpisodeModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EpisodeModel, newItem: EpisodeModel): Boolean {
                return oldItem == newItem
            }
        }
    }

}