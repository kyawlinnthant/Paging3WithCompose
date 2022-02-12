package com.example.paginationwithcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paginationwithcompose.common.Constants

@Entity(tableName = Constants.BREED_TABLE)
data class BreedEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val url: String,
    val height: String,
    val weight: String,
    val lifeSpan: String,
    val description: String? = null,
    val temperament: String? = null,
    val group: String? = null,
)