package com.example.paginationwithcompose.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paginationwithcompose.data.local.dao.BreedsDao
import com.example.paginationwithcompose.data.local.dao.RemoteKeyDao
import com.example.paginationwithcompose.data.local.entity.BreedEntity
import com.example.paginationwithcompose.data.local.entity.RemoteKeyEntity

@Database(
    entities = [BreedEntity::class, RemoteKeyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BreedsDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedsDao
    abstract fun remoteKeyDao(): RemoteKeyDao
}