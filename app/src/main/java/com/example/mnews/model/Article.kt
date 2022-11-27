package com.example.mnews.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ResponseArticle(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>,
)

class Article(
    @SerializedName("source")
    val source: Source?,
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    var publishedAt: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
)

class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
)