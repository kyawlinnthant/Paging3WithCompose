package com.example.paginationwithcompose.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.data.local.entity.BreedEntity

@Dao
interface BreedsDao {

    @Query("SELECT * FROM ${Constants.BREED_TABLE}")
    fun getBreeds(): PagingSource<Int, BreedEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBreeds(items: List<BreedEntity>)

    @Query("DELETE FROM ${Constants.BREED_TABLE}")
    suspend fun deleteBreeds()
}