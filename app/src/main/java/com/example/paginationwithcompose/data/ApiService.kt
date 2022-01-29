package com.example.paginationwithcompose.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("https://api.themoviedb.org/3/movie/upcoming?api_key=cdbea55de27a909b4aaa2cfc02eabb75")
    suspend fun fetchMovies(
        @Query("page") pageNumber: Int,
    ): Response<ListResponse>
}