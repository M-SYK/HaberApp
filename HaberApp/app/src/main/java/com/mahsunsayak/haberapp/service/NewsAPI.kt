package com.mahsunsayak.haberapp.service

import com.mahsunsayak.haberapp.model.NewsResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.Serializable

interface NewsAPI : Serializable {
    @GET("everything")
    fun getNewsByCategory(
        @Query("apiKey") apiKey: String,
        @Query("q") category: String,
        @Query("language") language: String
    ): Observable<NewsResponse>
}
