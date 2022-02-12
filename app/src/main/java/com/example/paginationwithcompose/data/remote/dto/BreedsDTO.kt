package com.example.paginationwithcompose.data.remote.dto

import com.example.paginationwithcompose.data.local.entity.BreedEntity
import com.example.paginationwithcompose.data.remote.vo.BreedItemVo

data class BreedItemDTO(
    val id: Int,
    val name: String,
    val description: String? = null,
    val reference_image_id: String,
    val bred_for: String,
    val breed_group: String?,
    val country_code: String,
    val height: Height,
    val history: String,
    val image: Image,
    val life_span: String,
    val origin: String,
    val temperament: String? = null,
    val weight: Weight
) {
    fun toVo(): BreedItemVo {
        return BreedItemVo(
            id = id,
            name = name,
            description = description,
            temperament = temperament,
            url = image.url,
            height = height.imperial,
            weight = weight.imperial,
            lifeSpan = life_span,
            group = breed_group
        )
    }

    fun toEntity(): BreedEntity {
        return BreedEntity(
            id = id,
            name = name,
            description = description,
            temperament = temperament,
            url = image.url,
            height = height.imperial,
            weight = weight.imperial,
            lifeSpan = life_span,
            group = breed_group
        )
    }
}

data class Height(
    val imperial: String,
    val metric: String
)

data class Image(
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)

data class Weight(
    val imperial: String,
    val metric: String
)