package com.example.paginationwithcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.paginationwithcompose.data.AppRepository
import com.example.paginationwithcompose.data.MoviePagingDataSource
import com.example.paginationwithcompose.data.dto.BreedsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val movies: Flow<PagingData<BreedsItem>> = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        MoviePagingDataSource(appRepository)
    }.flow.cachedIn(viewModelScope)
}