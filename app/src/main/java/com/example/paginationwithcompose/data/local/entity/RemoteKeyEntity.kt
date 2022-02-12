package com.example.paginationwithcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.paginationwithcompose.common.Constants

@Entity(tableName = Constants.BREED_REMOTE_KEYS_TABLE)
data class RemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?
)