package com.example.mnews.network

import com.example.mnews.model.ResponseArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MNewsAPI {

    companion object{
        const val API_KEY = "0d31c2b0e6174fb991eba4550d47bdd8"
    }

    @GET("https://newsapi.org/v2/top-headlines")
    suspend fun getNews(
        @Query("q") q: String = "",
        @Query("category") category: String = "",
        @Query("sources") sources: String = "",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<ResponseArticle>

    @GET("https://newsapi.org/v2/everything")
    suspend fun getNewsEverything(
        @Query("q") q: String = "",
        @Query("category") category: String = "",
        @Query("sources") sources: String = "",
        @Query("page") pageNumber: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("apiKey") apiKey: String = API_KEY,
    ): Response<ResponseArticle>
}