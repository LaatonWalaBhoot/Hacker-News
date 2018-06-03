package com.laatonwalabhoot.hackernews.data.remote

import com.laatonwalabhoot.hackernews.data.models.Article
import com.laatonwalabhoot.hackernews.data.models.Comment
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("topstories.json")
    fun getTopArticles(): Observable<List<Int>>

    @GET("item/{article_id}.json")
    fun getArticle(@Path(value = "article_id", encoded = true) articleId: String): Observable<Article>

    @GET("item/{comment_id}.json")
    fun getComment(@Path(value = "comment_id", encoded = true) commentId: String): Observable<Comment>
}