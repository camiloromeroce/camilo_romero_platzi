package com.example.lib.model.details

import com.google.gson.annotations.SerializedName

data class CharacterDetailObjectModel(
    @SerializedName("data") val data: CharacterDetailDataModel
)
