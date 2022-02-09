package com.example.paginationwithcompose.data

import com.example.paginationwithcompose.data.dto.BreedItem
import retrofit2.Response

interface AppRepository {
    suspend fun getBreedList(
        page : Int,
        loadSize : Int
    ) :  Response<List<BreedItem>>
}