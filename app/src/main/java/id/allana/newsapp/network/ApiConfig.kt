package id.allana.newsapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

     companion object {
         fun getApiService(): ApiService {
             /**
              * API RESPONSE INTERCEPTOR
              */
             val loggingInterceptor = HttpLoggingInterceptor()
                 .setLevel(HttpLoggingInterceptor.Level.BODY)

             /**
              * SET CLIENT
              */
             val client = OkHttpClient.Builder()
                 .addInterceptor(loggingInterceptor)
                 .build()

             /**
              * SET RETROFIT
              */
             val retrofit = Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .client(client)
                 .build()
             return retrofit.create(ApiService::class.java)
         }

         private const val BASE_URL = "https://newsapi.org/v2/"
         const val API_KEY = "8d467b4b6d414e739276a750786896ac"
         const val COUNTRY_CODE = "us"
         const val POPULARITY = "popularity"
     }

}