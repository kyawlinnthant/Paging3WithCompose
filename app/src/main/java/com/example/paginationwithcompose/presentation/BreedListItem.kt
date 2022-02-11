package com.example.paginationwithcompose.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.paginationwithcompose.data.vo.BreedItemVo
import com.google.accompanist.coil.rememberCoilPainter

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FoldAbleItem(
    breedItemVo: BreedItemVo,
    onClick: () -> Unit
) {
    //you can save expandedState by remember if you don't want to save it across scrolling
    var expandedState by rememberSaveable {
        mutableStateOf(false)
    }

    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )
    val image = rememberCoilPainter(
        request = breedItemVo.url,
        fadeIn = true,
        fadeInDurationMs = 500
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .clickable(
                onClick = onClick
            ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                    ,
                    text = breedItemVo.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h5
                )
                IconButton(
                    modifier = Modifier.rotate(rotationState),
                    onClick = { expandedState = !expandedState }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop Arrow"
                    )
                }
            }

            AnimatedVisibility(
                visible = expandedState
            ) {
                Column {
                    Text(
                        text = breedItemVo.temperament ?: "movie.bred_for",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = image,
                        contentDescription = null,
                        //16:9 = 1.7f
                        modifier = Modifier
                            .aspectRatio(1.7f, false)
                            .clip(MaterialTheme.shapes.medium)
                        ,
                        contentScale = ContentScale.FillWidth
                    )

                    breedItemVo.description?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }

            }

        }
    }
}
