package com.glacierpower.feature.character.character.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.ResultsModel
import com.glacierpower.feature.databinding.CharacterItemBinding
import util.ExtensionFunction.loadImage

class CharacterAdapter(private val characterListener: CharacterListener) :
    PagingDataAdapter<ResultsModel,
            CharacterAdapter.CharacterViewHolder>(diffUtilCallback) {

    class CharacterViewHolder(
        val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.binding.characterName.text = "${character?.name}"
        character?.image?.let { holder.binding.characterImage.loadImage(it) }
        if (character!!.id != 0) {
            holder.binding.characterStatus.text = "Status: ${character?.status}"
            holder.itemView.setOnClickListener {
                characterListener.getCharacterById(character?.id!!)

            }
        }

    }

    companion object {
        val diffUtilCallback = object : DiffUtil.ItemCallback<ResultsModel>() {
            override fun areItemsTheSame(oldItem: ResultsModel, newItem: ResultsModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ResultsModel, newItem: ResultsModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    suspend fun submitCharacterList(character: List<ResultsModel>) {
        val pagingData: PagingData<ResultsModel> = PagingData.from(character)
        submitData(pagingData)
    }

}