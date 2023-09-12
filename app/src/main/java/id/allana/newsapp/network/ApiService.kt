package id.allana.newsapp.network

import id.allana.newsapp.model.ResponseNews
import id.allana.newsapp.network.ApiConfig.Companion.API_KEY
import id.allana.newsapp.network.ApiConfig.Companion.COUNTRY_CODE
import id.allana.newsapp.network.ApiConfig.Companion.POPULARITY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * GET TOP HEADLINE NEWS - ID
     */
    @GET("top-headlines")
    fun getTopHeadlineNewsFromID(
        @Query("country") country: String = COUNTRY_CODE,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ResponseNews>

    /**
     * SEARCH NEWS
     */
    @GET("everything")
    fun getSearchNews(
        @Query("q") query: String,
        @Query("sortBy") sortBy: String = POPULARITY,
        @Query("apiKey") apiKey: String = API_KEY
    ): Call<ResponseNews>

}