package com.example.camilo_romero_platzi.presentation.ui.home.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.camilo_romero_platzi.R
import com.example.camilo_romero_platzi.databinding.ItemViewChaptersBinding
import com.example.camilo_romero_platzi.presentation.ui.common.buildImageUrl
import com.example.camilo_romero_platzi.presentation.ui.common.inflate
import com.example.camilo_romero_platzi.presentation.ui.common.loadUrl
import com.example.domain.model.home.CharacterItem

class HomeAdapter(private val listener: (CharacterItem) -> Unit) :
    ListAdapter<CharacterItem, HomeAdapter.ViewHolder>(DiffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_view_chapters, false)
        return ViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    inner class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val binding = ItemViewChaptersBinding.bind(itemView)
        fun bind(item: CharacterItem) = with(binding) {
            item.apply {
                nameChapter.text = name
                imageChapter.loadUrl(buildImageUrl(thumbnail.path, thumbnail.extension))
            }
        }
    }

    object DiffUtilCallback : DiffUtil.ItemCallback<CharacterItem>() {
        override fun areItemsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterItem, newItem: CharacterItem): Boolean =
            oldItem == newItem
    }
}
