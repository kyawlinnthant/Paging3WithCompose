package com.example.paginationwithcompose.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.paginationwithcompose.data.AppRepository
import com.example.paginationwithcompose.data.BreedsPagingDataSource
import com.example.paginationwithcompose.data.vo.UiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    val breeds: Flow<PagingData<UiModel>> = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        BreedsPagingDataSource(appRepository)
    }
        .flow
        .map { pagingData ->

            pagingData
                .filter { breedItemVo ->
                    breedItemVo.name.startsWith(
                        prefix = "A",
                        ignoreCase = true
                    )

                }
                .map { breedItemVoModel ->
                    UiModel.BreedModel(breedItemVoModel)
                }
                .insertSeparators<UiModel.BreedModel, UiModel> { before, after ->
                    when {
                        before == null -> UiModel.SeparatorModel("HEADER")
                        after == null -> UiModel.SeparatorModel("FOOTER")
                        /* shouldSeparate(before, after) -> UiModel.SeparatorModel(
                             "BETWEEN ITEMS $before AND $after"
                         )*/
                        // Return null to avoid adding a separator between two items.
                        else -> null
                    }

                }
        }
        .cachedIn(viewModelScope)
}