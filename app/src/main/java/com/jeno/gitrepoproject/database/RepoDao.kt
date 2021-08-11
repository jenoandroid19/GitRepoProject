package com.jeno.gitrepoproject.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jeno.gitrepoproject.jdos.RepoListData

@Dao
interface RepoDao {

    @Insert
    fun insertRepoLists(repoListData: RepoListData)

    @Query("SELECT * from repo_table")
    fun getAllRepoList():LiveData<List<RepoListData>>
}