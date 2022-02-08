package com.example.paginationwithcompose.data

import com.example.paginationwithcompose.data.dto.BreedsItem
import retrofit2.Response

interface AppRepository {
    suspend fun getBreedList(
        page : Int,
        loadSize : Int
    ) :  Response<List<BreedsItem>>
}