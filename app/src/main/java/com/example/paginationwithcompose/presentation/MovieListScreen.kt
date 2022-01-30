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
import com.example.paginationwithcompose.data.Movie
import kotlinx.coroutines.flow.Flow

@Composable
fun MovieListScreen(
    movies: Flow<PagingData<Movie>>
) {
    MovieListView(
        movies = movies,
    )
}

@Composable
fun MovieListView(
    movies: Flow<PagingData<Movie>>,
) {

    val movieList: LazyPagingItems<Movie> = movies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(4.dp)
    ) {
        items(movieList) { item ->
            FoldAbleItem(
                item!!,
                onClick = {
                    //todo : do some click action
                })
        }
        movieList.apply {

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
                    val e = movieList.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillParentMaxSize(),
                            onClickRetry = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = movieList.loadState.append as LoadState.Error
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
