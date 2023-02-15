package com.example.lib.model.detail_section

import com.google.gson.annotations.SerializedName

data class CharacterDetailSectionDataModel(
    @SerializedName("results") val results: List<CharacterDetailSectionItemModel> = emptyList()
)
