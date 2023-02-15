package com.example.camilo_romero_platzi.presentation.ui.detail_section.apadter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camilo_romero_platzi.R
import com.example.camilo_romero_platzi.databinding.ItemViewBinding
import com.example.camilo_romero_platzi.presentation.ui.common.buildImageUrl
import com.example.camilo_romero_platzi.presentation.ui.common.inflate
import com.example.camilo_romero_platzi.presentation.ui.common.loadUrlRounded
import com.example.domain.model.detail_section.CharacterDetailSectionItem

class ItemAdapter() :
    ListAdapter<CharacterDetailSectionItem, ItemAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewBinding.bind(itemView)
        fun bind(item: CharacterDetailSectionItem) = with(binding) {
            item.apply {
                this@with.name.text = title
                this@with.logoImageView.loadUrlRounded(
                    buildImageUrl(
                        thumbnail?.path, thumbnail?.extension
                    )
                )
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<CharacterDetailSectionItem>() {
        override fun areItemsTheSame(
            oldItem: CharacterDetailSectionItem, newItem: CharacterDetailSectionItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CharacterDetailSectionItem, newItem: CharacterDetailSectionItem
        ): Boolean = oldItem == newItem

    }
}