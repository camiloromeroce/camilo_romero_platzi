package com.example.lib.model.detail_section

import com.example.lib.model.common.ThumbnailModel
import com.google.gson.annotations.SerializedName

data class CharacterDetailSectionItemModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel?
)
