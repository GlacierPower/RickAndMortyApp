package com.glacierpower.feature.character.characterDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.EpisodeModel
import com.glacierpower.feature.databinding.EpisodeItemBinding

class CharacterEpisodeAdapter(private val characterEpisodeListener: CharacterEpisodeListener) :
    RecyclerView.Adapter<CharacterEpisodeAdapter.CharacterEpisodeViewHolder>() {
    private var oldposition = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterEpisodeViewHolder {
        return CharacterEpisodeViewHolder(
            EpisodeItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CharacterEpisodeViewHolder,
        position: Int
    ) {
        val episode = differ.currentList[position]
        holder.bind(episode)
        oldposition = holder.bindingAdapterPosition


    }

    private val differCallback =
        object : DiffUtil.ItemCallback<EpisodeModel>() {
            override fun areItemsTheSame(
                oldItem: EpisodeModel,
                newItem: EpisodeModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EpisodeModel,
                newItem: EpisodeModel
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class CharacterEpisodeViewHolder(private val itemBinding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(episodeModel: EpisodeModel) {
            itemBinding.episodeName.text = episodeModel.name
            itemBinding.episode.text = episodeModel.episode
            itemBinding.airDate.text = episodeModel.air_date
            itemView.setOnClickListener {
                characterEpisodeListener.getEpisodeById(episodeModel.id)
            }

        }
    }
}