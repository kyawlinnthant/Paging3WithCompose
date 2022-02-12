package com.example.paginationwithcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.paginationwithcompose.data.remote.vo.UiModel
import com.example.paginationwithcompose.repo.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appRepository: AppRepository
) : ViewModel() {

    val breedsFromApi: Flow<PagingData<UiModel>> = appRepository.getBreedsFromApi()
        .map { pagingData ->
            pagingData
                .map { breedItemVo ->
                    UiModel.BreedModel(breedItemVo)
                }
                .insertSeparators<UiModel.BreedModel, UiModel> { before, after ->
                    val nameOfAfterItem = after?.item?.name?.first().toString()
                    if (after?.item?.name?.first() != before?.item?.name?.first()) {
                        return@insertSeparators UiModel.SeparatorModel(nameOfAfterItem)
                    } else null
                }
        }.cachedIn(viewModelScope)
}
