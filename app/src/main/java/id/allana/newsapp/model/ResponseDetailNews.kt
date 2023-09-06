package id.allana.newsapp.model

import com.google.gson.annotations.SerializedName

data class ResponseDetailNews(

	@field:SerializedName("data")
	val data: News,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
