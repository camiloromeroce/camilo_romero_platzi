package com.example.domain.model.detail_section

import com.example.domain.home.common.EMPTY_STRING
import com.example.domain.home.common.ThumbnailItem

data class CharacterDetailSectionItem(
    val id: String,
    val title: String,
    val description: String? = EMPTY_STRING,
    val thumbnail: ThumbnailItem? = ThumbnailItem()
)
