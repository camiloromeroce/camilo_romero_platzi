package com.example.lib.model.details

import com.example.lib.model.common.ThumbnailModel
import com.google.gson.annotations.SerializedName

data class CharacterDetailModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel,
    @SerializedName("comics") val comics: DetailItemModel,
    @SerializedName("series") val series: DetailItemModel,
    @SerializedName("stories") val stories: DetailItemModel,
    @SerializedName("events") val events: DetailItemModel
)
