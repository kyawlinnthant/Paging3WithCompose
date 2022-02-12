package com.example.paginationwithcompose.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.paginationwithcompose.data.vo.UiModel
import kotlinx.coroutines.flow.Flow

@Composable
fun DoggoListScreen() {
    val vm: MainViewModel = hiltViewModel()
    DoggoListView(
        breedItems = vm.breeds,
    )
}

@Composable
fun DoggoListView(
    breedItems: Flow<PagingData<UiModel>>,
) {

    val breed: LazyPagingItems<UiModel> = breedItems.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {

        items(
            items = breed,
            key = { item -> item }
        ) { uiModel ->
            when (uiModel) {
                is UiModel.BreedModel -> {
                    FoldAbleItem(
                        breedItemVo = uiModel.item,
                        onClick = {
                            //todo : do some click action
                        })
                }
                is UiModel.SeparatorModel -> {

                    if (uiModel.header != "null"){
                        Text(
                            text = uiModel.header,
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                }
            }

        }

        breed.apply {

            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        LoadingView(modifier = Modifier.fillParentMaxSize())
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        LoadingItem()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = breed.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = breed.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append.endOfPaginationReached -> {
                    item {

                        Text(
                            text = "This is the end of our data",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        )
                    }

                }
            }
        }
    }
}
