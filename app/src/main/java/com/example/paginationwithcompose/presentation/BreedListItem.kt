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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            .padding(4.dp),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .clip(RoundedCornerShape(4.dp))
                .clickable(
                    onClick = onClick
                )
                .padding(12.dp)

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
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
                    breedItemVo.group?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Group", style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = it)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Life-span", style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(text = breedItemVo.lifeSpan)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = breedItemVo.temperament ?: "Specific Intelligence",
                        style = MaterialTheme.typography.body2
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        painter = image,
                        contentDescription = null,
                        //16:9 = 1.7f
                        modifier = Modifier
                            .aspectRatio(1.7f, false)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Height", style = MaterialTheme.typography.h4)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = breedItemVo.height + " in")
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Weight", style = MaterialTheme.typography.h4)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = breedItemVo.weight + " lb")
                        }
                    }
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
