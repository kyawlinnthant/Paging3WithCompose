package com.example.paginationwithcompose.data

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepository {
    suspend fun getPagingMovies(page : Int) :  Response<ListResponse>
}