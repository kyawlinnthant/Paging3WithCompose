package com.example.paginationwithcompose.data

import retrofit2.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    override suspend fun getPagingMovies(page: Int): Response<ListResponse> {
        return apiService.fetchMovies(page)
    }

}