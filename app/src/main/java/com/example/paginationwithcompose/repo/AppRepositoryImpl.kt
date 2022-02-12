package com.example.paginationwithcompose.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.data.local.db.BreedsDatabase
import com.example.paginationwithcompose.data.paging.BreedsPagingSource
import com.example.paginationwithcompose.data.paging.BreedsRemoteMediator
import com.example.paginationwithcompose.data.remote.ApiService
import com.example.paginationwithcompose.data.remote.vo.BreedItemVo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: BreedsDatabase
) : AppRepository {

    private val config = PagingConfig(
        pageSize = Constants.PAGE_SIZE,
        enablePlaceholders = false
    )
    private val remoteMediator = BreedsRemoteMediator(
        apiService = apiService,
        database = database
    )
    private val dbSourceFactory = database.breedDao().getBreeds()


    override fun getBreedsFromApi(): Flow<PagingData<BreedItemVo>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                BreedsPagingSource(
                    apiService = apiService
                )
            }
        ).flow
    }

  /*  override fun getBreedsFromDb(): Flow<PagingData<BreedItemVo>> {

        return Pager(
            config = config,
            initialKey = 1,
            pagingSourceFactory = dbSourceFactory,


            *//*{
                BreedsPagingSource(
                    apiService = apiService
                )
            },*//*
            remoteMediator = {
                BreedsRemoteMediator(
                    apiService = apiService,
                    database = database
                )
            }

        ).flow
    }*/

}
