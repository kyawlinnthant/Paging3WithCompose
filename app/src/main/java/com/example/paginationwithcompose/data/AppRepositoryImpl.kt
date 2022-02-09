package com.example.paginationwithcompose.data

import com.example.paginationwithcompose.data.dto.BreedsItem
import retrofit2.Response
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AppRepository {

    override suspend fun getBreedList(page: Int, loadSize: Int): Response<List<BreedsItem>> {
        return apiService.fetchBreeds(
            page = page,
            loadSize = loadSize
        )
    }

}