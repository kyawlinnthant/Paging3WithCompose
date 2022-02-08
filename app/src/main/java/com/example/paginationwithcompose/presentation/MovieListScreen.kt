package com.example.paginationwithcompose.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.paginationwithcompose.data.dto.BreedsItem
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieListScreen(
    movies: Flow<PagingData<BreedsItem>>
) {
    MovieListView(
        movies = movies,
    )
}

@Composable
fun MovieListView(
    movies: Flow<PagingData<BreedsItem>>,
) {

    val breeds: LazyPagingItems<BreedsItem> = movies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(4.dp)
    ) {

        items(breeds) { item ->
            FoldAbleItem(
                item!!.toVo(),
                onClick = {
                    //todo : do some click action
                })
        }
        breeds.apply {

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
                    val e = breeds.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = breeds.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onClickRetry = { retry() }
                        )
                    }
                }
            }
        }
    }
}
