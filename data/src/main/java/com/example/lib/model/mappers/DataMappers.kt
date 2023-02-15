package com.example.lib.model.mappers

import com.example.domain.home.common.EMPTY_DESCRIPTION
import com.example.domain.home.common.ThumbnailItem
import com.example.domain.model.detail.CharacterDetailItem
import com.example.domain.model.detail.DetailItemItem
import com.example.domain.model.detail.Item
import com.example.domain.model.detail_section.CharacterDetailSectionItem
import com.example.domain.model.home.CharacterItem
import com.example.lib.model.common.ThumbnailModel
import com.example.lib.model.detail_section.CharacterDetailSectionItemModel
import com.example.lib.model.details.CharacterDetailModel
import com.example.lib.model.details.DetailItemModel
import com.example.lib.model.details.ItemModel
import com.example.lib.model.home.CharacterModel

fun CharacterModel.toDomain(): CharacterItem = CharacterItem(
    id = id.toString(), name = name, thumbnail = thumbnail.toDomain()
)

fun ThumbnailModel.toDomain(): ThumbnailItem = ThumbnailItem(
    path = path, extension = extension
)

fun CharacterDetailModel.toDomain(): CharacterDetailItem = CharacterDetailItem(
    id = id.toString(),
    name = name,
    description = description.ifEmpty { EMPTY_DESCRIPTION },
    thumbnail = thumbnail.toDomain(),
    comics = comics.toDomain(),
    series = series.toDomain(),
    stories = stories.toDomain(),
    events = events.toDomain()
)

fun DetailItemModel.toDomain(): DetailItemItem = DetailItemItem(items = items.map { it.toDomain() })

fun ItemModel.toDomain(): Item = Item(
    name = name, resourceURI = resourceURI
)

fun CharacterDetailSectionItemModel.toDomain(): CharacterDetailSectionItem =
    CharacterDetailSectionItem(
        id = id.toString(),
        title = title,
        description = description,
        thumbnail = thumbnail?.toDomain()
    )
