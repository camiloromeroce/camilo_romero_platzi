package com.example.lib.model.home

import com.google.gson.annotations.SerializedName

data class CharacterDataModel(
    @SerializedName("results") val results: List<CharacterModel> = emptyList()
)
