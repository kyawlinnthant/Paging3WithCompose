package com.example.paginationwithcompose.data.dto

import com.example.paginationwithcompose.data.vo.Breed

data class BreedsItem(
    val bred_for: String,
    val breed_group: String,
    val country_code: String,
    val description: String? = null,
    val height: Height,
    val history: String,
    val id: Int,
    val image: Image,
    val life_span: String,
    val name: String,
    val origin: String,
    val reference_image_id: String,
    val temperament: String? = null,
    val weight: Weight
) {
    fun toVo(): Breed {
        return Breed(
            name = name,
            description = description,
            temperament = temperament,
            url = image.url
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