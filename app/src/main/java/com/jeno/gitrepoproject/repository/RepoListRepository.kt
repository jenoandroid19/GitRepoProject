package com.jeno.gitrepoproject.repository

import androidx.lifecycle.LiveData
import com.jeno.gitrepoproject.database.RepoDao
import com.jeno.gitrepoproject.jdos.RepoListData

class RepoListRepository(private val repoDao: RepoDao) {

    var allRepoList : LiveData<List<RepoListData>> = repoDao.getAllRepoList()

    fun insertRepoList(repoListData: RepoListData){
        repoDao.insertRepoLists(repoListData)
    }

}