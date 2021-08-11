package com.jeno.gitrepoproject.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.jeno.gitrepoproject.database.RepoDao
import com.jeno.gitrepoproject.database.RepoDatabase
import com.jeno.gitrepoproject.jdos.RepoListData
import com.jeno.gitrepoproject.network.RetrofitInstance
import com.jeno.gitrepoproject.network.RetrofitService
import com.jeno.gitrepoproject.repository.RepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class RepoListViewModel(application: Application) :AndroidViewModel(application){

    var apiResponse: MutableLiveData<Boolean> = MutableLiveData()
    var repoDatabase = RepoDatabase.getInstance(application)
    var repoDao : RepoDao = repoDatabase.repoDao
    var repoListRepository : RepoListRepository = RepoListRepository(repoDao)
    var allRepoList : LiveData<List<RepoListData>> = repoListRepository.allRepoList

    fun makeApiCall() {
        val retroInstance = RetrofitInstance.getRetroInstance().create(RetrofitService::class.java)
        val call = retroInstance.getDataFromAPI()
        call.enqueue(object : retrofit2.Callback<ArrayList<RepoListData>>{
            override fun onResponse(call: Call<ArrayList<RepoListData>>, response: Response<ArrayList<RepoListData>>) {
                if(response.isSuccessful) {
                    apiResponse.postValue(true)
                    for (repo in response.body()!!){
                        insertRepoList(repo)
                    }
                } else {
                    apiResponse.postValue(false)
                }
            }
            override fun onFailure(call: Call<ArrayList<RepoListData>>, t: Throwable) {
                apiResponse.postValue(false)
            }
        })
    }

    fun insertRepoList(repoListData: RepoListData)= viewModelScope.launch(Dispatchers.IO){
        repoListRepository.insertRepoList(repoListData)
    }
}