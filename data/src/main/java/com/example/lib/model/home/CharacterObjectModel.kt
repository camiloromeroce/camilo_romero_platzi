package com.example.lib.model.home

import com.google.gson.annotations.SerializedName

data class CharacterObjectModel(
    @SerializedName("data") val data: CharacterDataModel
)

