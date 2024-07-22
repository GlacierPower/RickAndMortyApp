package com.glacierpower.feature.episode.episodeDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.ResultsModel
import com.glacierpower.feature.databinding.CharacterItemBinding
import util.ExtensionFunction.loadImage

class EpisodeCharacterAdapter(private val episodeAdapterListener: EpisodeAdapterListener) :
    RecyclerView.Adapter<EpisodeCharacterAdapter.CharacterViewHolder>() {
    private var oldposition = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        val character = differ.currentList[position]
        holder.bind(character)
        oldposition = holder.bindingAdapterPosition


    }

    private val differCallback =
        object : DiffUtil.ItemCallback<ResultsModel>() {
            override fun areItemsTheSame(
                oldItem: ResultsModel,
                newItem: ResultsModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ResultsModel,
                newItem: ResultsModel
            ): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class CharacterViewHolder(private val itemBinding: CharacterItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(resultsModel: ResultsModel) {
            itemBinding.characterName.text = resultsModel.name
            itemBinding.characterStatus.text = resultsModel.status
            itemBinding.characterImage.loadImage(resultsModel.image)
            itemView.setOnClickListener {
                episodeAdapterListener.getCharacterById(resultsModel.id)
            }

        }
    }
}