package com.jeno.gitrepoproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        val baseURL = "https://gh-trending-api.herokuapp.com/"
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                    .baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }
}