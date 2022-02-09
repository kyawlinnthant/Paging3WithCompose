package com.example.paginationwithcompose.data

import com.example.paginationwithcompose.common.Endpoints
import com.example.paginationwithcompose.data.dto.BreedItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoints.BREEDS)
    suspend fun fetchBreeds(
        @Header(Endpoints.AUTHORIZATION) apiKey: String = "x-api-key ${Endpoints.API_KEY}",
        @Query("page") page: Int,
        @Query("limit") loadSize: Int,
    ): Response<List<BreedItem>>
}