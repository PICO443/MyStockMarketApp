package com.pico.mystockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query(value = "apiKkey") apiKey: String = API_KEY): ResponseBody


    companion object {
        private const val API_KEY = "TPN3WIJ1HIZSXM46"
        const val BASE_URL = "https://www.alphavantage.co/"
    }

}