package com.jeno.gitrepoproject.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jeno.gitrepoproject.jdos.RepoListData
import com.jeno.gitrepoproject.network.RetrofitInstance
import com.jeno.gitrepoproject.network.RetrofitService
import retrofit2.Call
import retrofit2.Response

class RepoListViewModel :ViewModel() {

    var repoListData: MutableLiveData<ArrayList<RepoListData>>


    init {
        repoListData = MutableLiveData()
    }


    fun getRecyclerListDataObserver(): MutableLiveData<ArrayList<RepoListData>> {
        return repoListData
    }

    fun makeApiCall() {
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.getDataFromAPI()
        call.enqueue(object : retrofit2.Callback<ArrayList<RepoListData>>{
            override fun onResponse(call: Call<ArrayList<RepoListData>>, response: Response<ArrayList<RepoListData>>) {
                if(response.isSuccessful) {
                    //recyclerViewAdapter.setListData(response.body()?.items!!)
                    //recyclerViewAdapter.notifyDataSetChanged()
                    repoListData.postValue(response.body())
                } else {
                    repoListData.postValue(null)
                }
            }

            override fun onFailure(call: Call<ArrayList<RepoListData>>, t: Throwable) {
                // Toast.makeText(this@RecyclerViewActivity, "Error in getting data from api.", Toast.LENGTH_LONG).show()

                repoListData.postValue(null)
            }
        })
    }
}