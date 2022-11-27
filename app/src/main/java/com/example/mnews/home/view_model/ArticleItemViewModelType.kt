package com.example.mnews.home.view_model

sealed class ArticleItemViewModelType

data class ArticleItemViewModel(
    val sourceName: String,
    val author: String,
    val title: String,
    val desc: String,
    val image: String,
    val date: String,
    val url: String
) : ArticleItemViewModelType()

data class ArticleLoadingItemViewModel(
    val isLoading: Boolean = true,
) : ArticleItemViewModelType()

data class ArticleEmptyErrorItemViewModel(
    val title: String = "",
    val desc: String,
) : ArticleItemViewModelType()

data class ArticleLoadMoreViewModel(
    val isLoading: Boolean = true
) : ArticleItemViewModelType()