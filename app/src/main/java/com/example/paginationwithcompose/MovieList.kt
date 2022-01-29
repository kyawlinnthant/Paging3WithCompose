package com.example.paginationwithcompose

import android.content.Context
import android.widget.Toast
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
fun MovieList(
    viewModel: MainViewModel,
    context: Context
) {
    MovieListView(
        movies = viewModel.movies,
        context
    )
}

@Composable
fun MovieListView(
    movies: Flow<PagingData<Movie>>,
    context: Context
) {

    val movie: LazyPagingItems<Movie> = movies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.padding(4.dp)
    ) {
        items(movie) { item ->
            FoldAbleItem(
                item!!,
                onClick = {
                    Toast.makeText(context, item.original_title, Toast.LENGTH_SHORT).show()
                })
        }
        movie.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    //You can add modifier to manage load state when first time response page is loading
                }
                loadState.append is LoadState.Loading -> {
                    //You can add modifier to manage load state when next response page is loading
                }
                loadState.append is LoadState.Error -> {
                    //You can use modifier to show error message
                }
            }
        }
    }
}
