package com.example.paginationwithcompose.repo

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.paginationwithcompose.data.local.entity.BreedEntity
import com.example.paginationwithcompose.data.local.entity.RemoteKeyEntity
import com.example.paginationwithcompose.data.remote.dto.BreedItemDTO
import com.example.paginationwithcompose.data.remote.vo.BreedItemVo
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AppRepository {

    fun getBreedsFromApi() : Flow<PagingData<BreedItemVo>>

//    fun getBreedsFromDb() : Flow<PagingData<BreedItemVo>>
}