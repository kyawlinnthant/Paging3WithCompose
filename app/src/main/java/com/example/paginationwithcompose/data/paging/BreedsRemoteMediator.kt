package com.example.paginationwithcompose.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.common.Endpoints
import com.example.paginationwithcompose.data.local.db.BreedsDatabase
import com.example.paginationwithcompose.data.local.entity.RemoteKeyEntity
import com.example.paginationwithcompose.data.remote.ApiService
import com.example.paginationwithcompose.data.remote.vo.BreedItemVo
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class BreedsRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val database: BreedsDatabase
) : RemoteMediator<Int, BreedItemVo>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedItemVo>
    ): MediatorResult {

        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }

            }
            val response = apiService.fetchBreeds(
                apiKey = Endpoints.API_KEY,
                page = currentPage,
                loadSize = Constants.ITEM_REQUEST
            )

            val pageResponse = response.body()?.map {
                it.toEntity()
            }

            val endOfPaginationReached = pageResponse.isNullOrEmpty()
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.breedDao().deleteBreeds()
                    database.remoteKeyDao().deleteRemoteKeys()
                }
                val keys = response.body()?.map {
                    RemoteKeyEntity(
                        id = it.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                database.breedDao().addBreeds(items = pageResponse!!)
                database.remoteKeyDao().addRemoteKeys(remoteKeys = keys!!)

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)


        } catch (e: Exception) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, BreedItemVo>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let {
                database.remoteKeyDao().getRemoteKey(id = it)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, BreedItemVo>
    ): RemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                database.remoteKeyDao().getRemoteKey(id = it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, BreedItemVo>
    ): RemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let {
                database.remoteKeyDao().getRemoteKey(id = it.id)
            }
    }
}