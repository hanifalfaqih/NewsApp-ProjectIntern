package id.allana.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.allana.newsapp.model.ResponseNews
import id.allana.newsapp.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Locale

class NewsViewModel: ViewModel() {

    private val _newsData = MutableLiveData<ResponseNews?>()
    val newsData: LiveData<ResponseNews?> get() = _newsData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> get() = _isError

    var errorMessage: String = ""
        private set

    fun getAllNews() {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getAllNews()
        client.enqueue(object: Callback<ResponseNews> {
            override fun onResponse(call: Call<ResponseNews>, response: Response<ResponseNews>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null) {
                    onError("Error processing data!")
                    return
                }

                _isLoading.value = false
                _newsData.postValue(responseBody)
            }

            override fun onFailure(call: Call<ResponseNews>, t: Throwable) {
                onError(t.message)
                t.printStackTrace()
            }
        })
    }

    private fun onError(inputMessage: String?) {
        val message = if (inputMessage.isNullOrBlank() or inputMessage.isNullOrEmpty()) "Unknown error"
        else inputMessage

        errorMessage = StringBuilder("ERROR: ")
            .append("$message some data may not displayed properly").toString()

        _isError.value = true
        _isLoading.value = false
    }
}