package com.example.domain.model.detail

import com.example.domain.home.common.ThumbnailItem

data class CharacterDetailItem(
    val id: String,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailItem,
    val comics: DetailItemItem,
    val series: DetailItemItem,
    val stories: DetailItemItem,
    val events: DetailItemItem,
)
