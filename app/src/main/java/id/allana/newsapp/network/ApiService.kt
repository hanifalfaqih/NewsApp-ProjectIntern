package id.allana.newsapp.network

import id.allana.newsapp.model.ResponseDetailNews
import id.allana.newsapp.model.ResponseNews
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /**
     * GET ALL NEWS
     */
    @GET("news")
    fun getAllNews(): Call<ResponseNews>

    /**
     * GET DETAIL NEWS
     */
    @GET("news/{id}")
    fun getDetailNews(
        @Path("id") id: String
    ): Call<ResponseDetailNews>

    /**
     * SEARCH NEWS
     */
    @GET("news/")
    fun searchNews(
        @Query("query") query: String
    ): Call<ResponseNews>
}