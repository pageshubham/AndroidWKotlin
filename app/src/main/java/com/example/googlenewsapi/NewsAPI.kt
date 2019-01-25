package com.example.googlenewsapi

import com.example.googlenewsapi.beans.Articles
import retrofit2.Call
import retrofit2.http.GET

interface NewsAPI {
    @GET("v2/top-headlines?sources=google-news-in&apiKey=96e3942ff23c4ea38bcfa86a490bd933")
    fun getNews(): Call<Articles>
}