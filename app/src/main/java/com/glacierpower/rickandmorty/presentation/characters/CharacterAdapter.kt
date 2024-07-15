package com.glacierpower.rickandmorty.presentation.characters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.domain.model.ResultsModel
import com.glacierpower.rickandmorty.databinding.CharacterItemBinding
import com.glacierpower.rickandmorty.util.ExtensionFunction.loadImage

class CharacterAdapter() : PagingDataAdapter<ResultsModel,
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
        holder.binding.characterStatus.text = "Status: ${character?.status}"
        character?.image?.let { holder.binding.characterImage.loadImage(it) }
        Log.i("BindViewHolder","Success")

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

}