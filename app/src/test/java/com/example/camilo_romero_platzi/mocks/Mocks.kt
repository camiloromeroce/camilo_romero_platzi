package com.example.camilo_romero_platzi.mocks

import com.example.domain.home.common.ThumbnailItem
import com.example.domain.model.detail.CharacterDetailItem
import com.example.domain.model.detail.DetailItemItem
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.domain.model.home.CharacterItem

const val sectionMock = "sectionMock"

val characterItemMock = CharacterItem(
    id = "1234",
    name = "CamiloTest",
    thumbnail = ThumbnailItem()
)

val characterItemDetailMock = CharacterDetailItem(
    id = "1234",
    name = "CamiloTest",
    description = "mock",
    thumbnail = ThumbnailItem(),
    comics = DetailItemItem(emptyList()),
    series = DetailItemItem(emptyList()),
    stories = DetailItemItem(emptyList()),
    events = DetailItemItem(emptyList())
)

val characterItemDetailSectionMock = CharacterDetailSectionItem(
    id = "12345",
    title = "ItemTest",
    description = "mock",
    thumbnail = ThumbnailItem()
)