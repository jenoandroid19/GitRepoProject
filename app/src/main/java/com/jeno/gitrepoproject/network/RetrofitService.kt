package com.jeno.gitrepoproject.network

import com.jeno.gitrepoproject.jdos.RepoListData
import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {

    @GET("repositories")
    fun getDataFromAPI(): Call<ArrayList<RepoListData>>
}