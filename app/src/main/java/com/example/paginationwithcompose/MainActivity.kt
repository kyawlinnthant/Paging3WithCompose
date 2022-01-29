package com.example.paginationwithcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.paginationwithcompose.ui.theme.PaginationWithComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PaginationWithComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.secondary) {
                    MovieList(
                        viewModel = viewModel,
                        context = this
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PaginationWithComposeTheme {
        MovieList(viewModel = hiltViewModel(), context = MainActivity::class.java.newInstance())
    }
}

