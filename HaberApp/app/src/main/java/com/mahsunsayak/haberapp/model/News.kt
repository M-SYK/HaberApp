package com.mahsunsayak.haberapp.model

import java.io.Serializable

data class NewsResponse(val articles: List<News>)

data class News(
    val title: String,
    val urlToImage: String,
    val content: String,
    val author: String, // Yazarı ekle
    val publishedAt: String, // Yayınlanma tarihi ekle
    val description: String, // Açıklama ekle
    val url: String, // Haber detayı URL'si ekle
) : Serializable {

}
