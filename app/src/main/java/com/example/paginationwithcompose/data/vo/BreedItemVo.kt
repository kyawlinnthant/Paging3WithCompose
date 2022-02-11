package com.example.paginationwithcompose.data.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BreedItemVo(
    val name: String,
    val description: String? = null,
    val temperament: String? = null,
    val url: String,
) : Parcelable

sealed class UiModel {
    @Parcelize
    data class BreedModel(val item: BreedItemVo) : UiModel(), Parcelable

    @Parcelize
    data class SeparatorModel(val header: String) : UiModel(), Parcelable
}
