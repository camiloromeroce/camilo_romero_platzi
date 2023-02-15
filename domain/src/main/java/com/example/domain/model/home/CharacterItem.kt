package com.example.domain.model.home

import com.example.domain.home.common.ThumbnailItem

data class CharacterItem(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailItem,
)