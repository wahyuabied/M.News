package com.example.mnews.network

import com.example.mnews.model.ResponseArticle
import retrofit2.Response
import javax.inject.Inject

class MNewsRepositoryImpl @Inject constructor(private val api: MNewsAPI) : MNewsRepository {
    override suspend fun getNews(
        q: String,
        category: String,
        sources: String,
        page: Int,
        pageNumber: Int,
    ): Response<ResponseArticle> {
        return api.getNews(q, category, sources, page, pageNumber)
    }

    override suspend fun getNewsEverything(
        q: String,
        category: String,
        sources: String,
        page: Int,
        pageNumber: Int,
    ): Response<ResponseArticle> {
        return api.getNewsEverything(q, category, sources, page, pageNumber)
    }
}