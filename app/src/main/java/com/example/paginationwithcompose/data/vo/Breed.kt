package com.example.paginationwithcompose.data.vo

data class Breed(
    val name: String,
    val description: String? = null,
    val temperament: String? = null,
    val url: String,
    var isExpended : Boolean = false
)
