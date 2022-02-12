package com.example.paginationwithcompose.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationwithcompose.common.Constants
import com.example.paginationwithcompose.common.Endpoints
import com.example.paginationwithcompose.data.remote.ApiService
import com.example.paginationwithcompose.data.remote.vo.BreedItemVo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class BreedsPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, BreedItemVo>() {

    override fun getRefreshKey(state: PagingState<Int, BreedItemVo>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BreedItemVo> {

        val currentPage = params.key ?: Constants.INITIAL_PAGE

        return try {
            val response = apiService.fetchBreeds(
                apiKey = Endpoints.API_KEY,
                page = currentPage,
                loadSize = Constants.ITEMS_PER_PAGE
            )
            val pageResponse = response.body()?.map {
                it.toVo()
            }
            val endOfPaginationReached = pageResponse.isNullOrEmpty()
            if (endOfPaginationReached) {
                LoadResult.Page(
                    data = pageResponse.orEmpty(),
                    prevKey = null, //nextPage ( we use forward only )prevPage,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = pageResponse.orEmpty(),
                    prevKey = null, //nextPage ( we use forward only )prevPage,
                    nextKey = currentPage + 1
                )
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}