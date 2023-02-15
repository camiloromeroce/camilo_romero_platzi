package com.example.lib.model.home

import com.example.lib.model.common.ThumbnailModel
import com.google.gson.annotations.SerializedName

data class CharacterModel(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail") val thumbnail: ThumbnailModel,
)
