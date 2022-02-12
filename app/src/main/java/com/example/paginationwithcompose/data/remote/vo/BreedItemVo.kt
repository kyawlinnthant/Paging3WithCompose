package com.example.paginationwithcompose.data.remote.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedItemVo(
    val id: Int,
    val name: String,
    val url: String,
    val height: String,
    val weight: String,
    val lifeSpan: String,
    val description: String? = null,
    val temperament: String? = null,
    val group: String? = null,
    ) : Parcelable

sealed class UiModel {
    @Parcelize
    data class BreedModel(val item: BreedItemVo) : UiModel(), Parcelable

    @Parcelize
    data class SeparatorModel(val header: String) : UiModel(), Parcelable
}
