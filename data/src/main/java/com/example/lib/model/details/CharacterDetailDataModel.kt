package com.example.lib.model.details

import com.google.gson.annotations.SerializedName

data class CharacterDetailDataModel(
    @SerializedName("results") val results: List<CharacterDetailModel> = emptyList()
)
