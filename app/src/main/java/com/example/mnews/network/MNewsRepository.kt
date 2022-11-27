package com.example.mnews.network

import com.example.mnews.model.ResponseArticle
import retrofit2.Response

interface MNewsRepository {
    suspend fun getNews(
        q: String = "",
        category: String = "",
        sources: String = "",
        page: Int = 1,
        pageNumber: Int = 10,
    ):Response<ResponseArticle>

    suspend fun getNewsEverything(
        q: String = "",
        category: String = "",
        sources: String = "",
        page: Int = 1,
        pageNumber: Int = 10,
    ):Response<ResponseArticle>
}